package com.ksp.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ExtendedRepository<T, I extends Serializable> extends JpaRepository<T, I> {
	@Override
	<S extends T> List<S> saveAll(Iterable<S> entities);

	@Override
	<S extends T> S save(S entity);

	@Override
	<S extends T> S saveAndFlush(S entity);

	T get(I id);

	T getAny(I id);

	T delete(I id);

	@Override
	void delete(T entity);

	void scheduleSoftDelete(T entity, Date date);

	void scheduleSoftDelete(I id, Date date);

	@Override
	List<T> findAll();
}