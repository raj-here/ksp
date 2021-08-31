package com.ksp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksp.domain.Book;
import com.ksp.repository.impl.IBookRepository;

@Repository
@Transactional
public interface BookRepository extends IBookRepository, ExtendedRepository<Book, String> {

}
