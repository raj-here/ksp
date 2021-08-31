package com.ksp.repository.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ksp.domain.common.Auditable;
import com.ksp.repository.ExtendedRepository;
import com.ksp.utils.RequestUtils;
import com.ksp.utils.predicate.NotDeleted;

public class ExtendedRepositoryImpl<T, I extends Serializable> extends SimpleJpaRepository<T, I>
		implements ExtendedRepository<T, I> {
	private final JpaEntityInformation<T, ?> entityInformation;
	private final EntityManager em;

	public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.em = entityManager;
	}

	@Override
	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		for (S entity : entities) {
			if (entity instanceof Auditable) {
				setAuditable(entity);
			}
		}
		return super.saveAll(entities);
	}

	private <S extends T> void setAuditable(S entity) {
		Long longDate = new Date().getTime();
		String user = RequestUtils.loggedInUser();
		Auditable eAuditable = ((Auditable) entity);
		if (eAuditable.getCreatedBy() == null) {
			eAuditable.setCreatedBy(user);
			eAuditable.setDateCreated(longDate);
		}
		eAuditable.setUpdatedBy(user);
		eAuditable.setLastUpdated(longDate);
	}

	@Override
	public <S extends T> S save(S entity) {
		if (entity instanceof Auditable) {
			setAuditable(entity);
		}
		return super.save(entity);
	}

	@Override
	public <S extends T> S saveAndFlush(S entity) {
		if (entity instanceof Auditable) {
			setAuditable(entity);
		}
		return super.saveAndFlush(entity);
	}

	@Override
	public T get(I id) {
		Optional<T> returned = super.findOne(Specification
				.where(new ByIdSpecification<T, I>(entityInformation, id)).and(notDeleted()));
		return returned.isPresent() ? returned.get() : null;
	}

	@Override
	public T getAny(I id) {
		T found = em.find(this.getDomainClass(), id);
		return found == null ? null : found;
	}

	@Override
	public void delete(T entity) {

		softDelete(entity, new Date().getTime());
	}

	@Override
	public T delete(I id) {
		T entity = get(id);
		delete(entity);
		return entity;
	}

	@Override
	@Transactional
	public void scheduleSoftDelete(I id, Date date) {
		T entity = get(id);
		softDelete(entity, date.getTime());
	}

	@Override
	@Transactional
	public void scheduleSoftDelete(T entity, Date date) {
		softDelete(entity, date.getTime());
	}

	@Override
	public List<T> findAll() {
		return super.findAll(notDeleted());
	}

	private void softDelete(T entity, Long longDate) {
		Assert.notNull(entity, "The entity must not be null!");

		CriteriaBuilder cb = em.getCriteriaBuilder();
		Class<T> domainClass = getDomainClass();
		CriteriaUpdate<T> update = cb.createCriteriaUpdate(domainClass);

		Root<T> root = update.from(domainClass);

		update.set(NotDeleted.DELETED_FIELD, longDate);

		final List<Predicate> predicates = new ArrayList<>();

		if (entityInformation.hasCompositeId()) {
			for (String s : entityInformation.getIdAttributeNames())
				predicates.add(cb.equal(root.<I>get(s), entityInformation
						.getCompositeIdAttributeValue(entityInformation.getId(entity), s)));
			update.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			update.where(cb.equal(root.<I>get(entityInformation.getIdAttribute().getName()),
					entityInformation.getId(entity)));
		}

		em.createQuery(update).executeUpdate();
	}

	private static final class ByIdSpecification<T, I extends Serializable>
			implements Specification<T> {
		private static final long serialVersionUID = 10000011L;
		private transient JpaEntityInformation<T, ?> entityInformation;
		private final I id;

		public ByIdSpecification(JpaEntityInformation<T, ?> entityInformation, I id) {
			this.entityInformation = entityInformation;
			this.id = id;
		}

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			final List<Predicate> predicates = new ArrayList<>();
			if (entityInformation.hasCompositeId()) {
				for (String s : entityInformation.getIdAttributeNames())
					predicates.add(cb.equal(root.<I>get(s),
							entityInformation.getCompositeIdAttributeValue(id, s)));

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			return cb.equal(root.<I>get(entityInformation.getIdAttribute().getName()), id);
		}
	}

	private static final class DeletedIsNull<T> implements Specification<T> {
		private static final long serialVersionUID = 5583309246531626571L;

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			return cb.isNull(root.<LocalDateTime>get(NotDeleted.DELETED_FIELD));
		}
	}

	private static final class DeletedTimeGreatherThanNow<T> implements Specification<T> {
		private static final long serialVersionUID = 2335657687928203198L;

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			return cb.greaterThan(root.<Long>get(NotDeleted.DELETED_FIELD), new Date().getTime());
		}
	}

	private static final <T> Specification<T> notDeleted() {
		return Specification.where(new DeletedIsNull<T>()).or(new DeletedTimeGreatherThanNow<T>());
	}
}
