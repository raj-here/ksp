package com.ksp.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.ksp.bo.AuthorBo;
import com.ksp.bo.ListBo;
import com.ksp.cmd.PageCmd;
import com.ksp.domain.Author;
import com.ksp.utils.predicate.NotDeleted;
import com.ksp.utils.predicate.PredicateUtils;
import com.ksp.utils.predicate.UserPredicate;

public class AuthorRepositoryImpl implements IAuthorRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<AuthorBo> list(PageCmd page) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AuthorBo> criteriaQuery = builder.createQuery(AuthorBo.class);
		Root<Author> root = criteriaQuery.from(Author.class);
		PredicateUtils.getSortable(criteriaQuery, builder, root.get(page.getSortOn()),
				page.getSortOrder());
		criteriaQuery.where(autocompleteAuthorPredicate(builder, root, page.getSearchValue()));
		criteriaQuery.multiselect(root.get("id"), root.get("name"));
		return PredicateUtils.getResultList(entityManager.createQuery(criteriaQuery),
				ListBo.DEFAULT_PAGE_SIZE, page.getPageNumber());
	}

	@Override
	public Long totalCount(PageCmd page) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> count = builder.createQuery(Long.class);
		Root<Author> root = count.from(Author.class);
		count.where(autocompleteAuthorPredicate(builder, root, page.getSearchValue()));
		count.select(builder.count(root));
		return entityManager.createQuery(count).getSingleResult();
	}
	private <T> Predicate[] autocompleteAuthorPredicate(CriteriaBuilder builder, Root<T> root,
			String value) {
		List<Predicate> list = new ArrayList<>();
		list.add(NotDeleted.notDeleted(builder, root));
		if (!StringUtils.isEmpty(value))
			list.add(builder.like(root.get("name"), PredicateUtils.like(value)));
		list.add(UserPredicate.currentUser(builder, root));
		return PredicateUtils.toPredicateArray(list);
	}

	@Override
	public Author getByUser(String id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Author> criteriaQuery = builder.createQuery(Author.class);
		Root<Author> root = criteriaQuery.from(Author.class);
		criteriaQuery.where(get(builder, root, id));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	private <T> Predicate[] get(CriteriaBuilder builder, Root<T> root, String id) {

		List<Predicate> list = new ArrayList<>();
		list.add(NotDeleted.notDeleted(builder, root));
		list.add(builder.equal(root.get("id"), id));
		list.add(UserPredicate.currentUser(builder, root));
		return PredicateUtils.toPredicateArray(list);
	}


}
