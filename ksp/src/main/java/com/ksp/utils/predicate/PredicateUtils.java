package com.ksp.utils.predicate;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort.Direction;

public class PredicateUtils {
	private PredicateUtils() {

	}

	public static Predicate[] toPredicateArray(List<Predicate> conditions) {
		conditions = conditions.stream().filter(PredicateUtils::isNotNull)
				.collect(Collectors.toList());
		return conditions.toArray(new Predicate[conditions.size()]);
	}

	private static <T> boolean isNotNull(final T item) {
		return item != null;
	}

	/**
	 * 
	 * @param typedQuery
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public static <T> List<T> getResultList(TypedQuery<T> typedQuery, int pageSize,
			int pageNumber) {
		typedQuery.setFirstResult(pageNumber * pageSize);
		typedQuery.setMaxResults(pageSize);
		return typedQuery.getResultList();
	}

	public static <T> TypedQuery<T> getPagebleQuery(TypedQuery<T> typedQuery, int pageSize,
			int pageNumber) {
		typedQuery.setFirstResult(pageNumber * pageSize);
		typedQuery.setMaxResults(pageSize);
		return typedQuery;
	}

	public static <T, O> void getSortable(CriteriaQuery<T> cq, CriteriaBuilder cb, Path<O> property,
			Direction direction) {
		cq.orderBy(direction.isAscending() ? cb.asc(property) : cb.desc(property));
	}

	public static <T> Path<Object> getPath(Root<T> root, String prop) {
		if (prop.indexOf('.') >= 0) {
			String[] paths = prop.split("\\.");
			Path<Object> p = root.get(paths[0]);
			for (int i = 1; i < paths.length; i++) {
				p = p.get(paths[i]);
			}
			return p;
		}
		return root.get(prop);
	}

	public static <T> Path<String> getLikePath(Root<T> root, String prop) {
		if (prop.indexOf('.') >= 0) {
			String[] paths = prop.split("\\.");
			Path<String> p = root.get(paths[0]);
			for (int i = 1; i < paths.length; i++) {
				p = p.get(paths[i]);
			}
			return p;
		}
		return root.get(prop);
	}

	public static String like(String value) {
		return "%" + value + "%";
	}
}
