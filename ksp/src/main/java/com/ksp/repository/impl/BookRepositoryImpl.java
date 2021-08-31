package com.ksp.repository.impl;

import static com.ksp.enums.BookStatus.NONE;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.ksp.bo.BookBo;
import com.ksp.bo.BookSearchBo;
import com.ksp.bo.ListBo;
import com.ksp.cmd.PageCmd;
import com.ksp.domain.Author;
import com.ksp.domain.Book;
import com.ksp.utils.predicate.NotDeleted;
import com.ksp.utils.predicate.PredicateUtils;
import com.ksp.utils.predicate.UserPredicate;

public class BookRepositoryImpl implements IBookRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<BookSearchBo> homeSearch(PageCmd page) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BookSearchBo> criteriaQuery = builder.createQuery(BookSearchBo.class);
		Root<Book> root = criteriaQuery.from(Book.class);
		PredicateUtils.getSortable(criteriaQuery, builder, root.get(page.getSortOn()),
				page.getSortOrder());
		criteriaQuery.where(this.homePredicate(builder, root, page.getSearchValue()));
		criteriaQuery.multiselect(root.get("id"), root.get("name"), root.get("edition"),
				root.get("bookStatus"));
		return PredicateUtils.getResultList(entityManager.createQuery(criteriaQuery),
				ListBo.DEFAULT_PAGE_SIZE, page.getPageNumber());
	}

	@Override
	public Long totalCount(PageCmd page) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> count = builder.createQuery(Long.class);
		Root<Book> root = count.from(Book.class);
		count.where(this.homePredicate(builder, root, page.getSearchValue()));
		count.select(builder.count(root));
		return entityManager.createQuery(count).getSingleResult();
	}

	private <T> Predicate[] homePredicate(CriteriaBuilder builder, Root<T> root, String value) {
		List<Predicate> list = new ArrayList<>();
		list.add(NotDeleted.notDeleted(builder, root));
		if (!StringUtils.isEmpty(value))
			list.add(this.likeBookPredicate(builder, root, value));
		list.add(UserPredicate.notCurrentUser(builder, root));
		return PredicateUtils.toPredicateArray(list);
	}

	private <T> Predicate likeBookPredicate(CriteriaBuilder builder, Root<T> root, String value) {
		Join<Book, Author> authorJoin = root.join("author", JoinType.LEFT);
		value = PredicateUtils.like(value);
		return builder.or(builder.like(root.get("name"), value),
				builder.like(authorJoin.get("name"), value));
	}

	@Override
	public BookBo getPulishedBook(String id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BookBo> criteriaQuery = builder.createQuery(BookBo.class);
		Root<Book> root = criteriaQuery.from(Book.class);
		criteriaQuery.where(this.pulishedBookPredicate(builder, root, id));
		criteriaQuery.multiselect(root.get("id"), root.get("name"), root.get("edition"),
				root.get("bookStatus"));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	private <T> Predicate[] pulishedBookPredicate(CriteriaBuilder builder, Root<T> root,
			String id) {
		List<Predicate> list = new ArrayList<>();
		list.add(NotDeleted.notDeleted(builder, root));
		list.add(builder.equal(root.get("id"), id));
		list.add(builder.equal(root.get("bookStatus"), NONE));
		list.add(UserPredicate.notCurrentUser(builder, root));
		return PredicateUtils.toPredicateArray(list);
	}
}
