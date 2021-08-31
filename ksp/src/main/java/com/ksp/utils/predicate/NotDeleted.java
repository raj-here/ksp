package com.ksp.utils.predicate;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NotDeleted {
	public static final String DELETED_FIELD = "deletedOn";

	private NotDeleted() {

	}

	public static final <T> Predicate notDeleted(CriteriaBuilder builder, Root<T> root) {
		return builder.or(builder.isNull(root.get(DELETED_FIELD)),
				builder.greaterThan(root.<Long>get(DELETED_FIELD), new Date().getTime()));
	}

	public static final <Z, X> Predicate notDeleted(CriteriaBuilder builder, Join<Z, X> join) {
		return builder.or(builder.isNull(join.get(DELETED_FIELD)),
				builder.greaterThan(join.<Long>get(DELETED_FIELD), new Date().getTime()));
	}
}