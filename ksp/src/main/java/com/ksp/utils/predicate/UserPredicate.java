package com.ksp.utils.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ksp.utils.RequestUtils;

public class UserPredicate {
	private UserPredicate() {

	}

	public static final <T> Predicate currentUser(CriteriaBuilder builder, Root<T> root) {
		return createdBy(builder, root, RequestUtils.loggedInUser());
	}

	public static final <T> Predicate createdBy(CriteriaBuilder builder, Root<T> root,
			String userId) {
		return builder.equal(root.<String>get("createdBy"), userId);
	}

	public static final <T> Predicate notCurrentUser(CriteriaBuilder builder, Root<T> root) {
		return builder.notEqual(root.<String>get("createdBy"), RequestUtils.loggedInUser());
	}

}
