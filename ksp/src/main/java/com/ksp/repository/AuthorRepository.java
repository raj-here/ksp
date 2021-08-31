package com.ksp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksp.domain.Author;
import com.ksp.repository.impl.IAuthorRepository;

@Repository
@Transactional
public interface AuthorRepository extends ExtendedRepository<Author, String>, IAuthorRepository {

}
