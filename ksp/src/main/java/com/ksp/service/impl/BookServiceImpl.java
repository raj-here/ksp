package com.ksp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ksp.bo.BookBo;
import com.ksp.bo.BookSearchBo;
import com.ksp.bo.ListBo;
import com.ksp.bo.OwerOverviewBo;
import com.ksp.cmd.BookCmd;
import com.ksp.cmd.PageCmd;
import com.ksp.domain.Author;
import com.ksp.domain.Book;
import com.ksp.repository.AuthorRepository;
import com.ksp.repository.BookRepository;
import com.ksp.service.BookService;

import lombok.NonNull;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	@Transactional
	public BookBo saveBook(BookCmd bookCmd) {
		Book book = new Book(bookCmd.getName(), bookCmd.getEdition(), bookCmd.getDescription(),
				bookCmd.getBookStatus(), bookCmd.getBookCondition());
		if (StringUtils.isEmpty(bookCmd.getAuthor())) {
			if (bookCmd.isNewAuthors()) {
				book.setAuthor(new Author(bookCmd.getAuthor()));
			} else {
				book.setAuthor(authorRepository.get(bookCmd.getAuthor()));
			}
		}
		this.bookRepository.saveAndFlush(book);
		return new BookBo(book, book.getAuthor());
	}

	@Override
	public BookBo updateBook(@NonNull String id, BookCmd bookCmd) {
		Book book = bookRepository.get(id);
		book.setName(bookCmd.getName());
		book.setEdition(bookCmd.getEdition());
		book.setDescription(bookCmd.getDescription());
		book.setBookStatus(bookCmd.getBookStatus());
		book.setBookCondition(bookCmd.getBookCondition());
		if (StringUtils.isEmpty(bookCmd.getAuthor())) {
			if (bookCmd.isNewAuthors()) {
				book.setAuthor(new Author(bookCmd.getAuthor()));
			} else {
				book.setAuthor(authorRepository.get(bookCmd.getAuthor()));
			}
		}
		this.bookRepository.saveAndFlush(book);
		return new BookBo(book, book.getAuthor());
	}

	@Override
	public ListBo<BookBo> list() {
		List<BookBo> bookBos = new ArrayList<>(20);
		for (int j = 1; j < 21; j++) {
			bookBos.add(new BookBo("Book" + j));
		}
		ListBo<BookBo> listBo = new ListBo<>();
		listBo.setPageNumber(0);
		listBo.setPageSize(25);
		listBo.setTotalCount(20);
		listBo.setData(bookBos);
		return listBo;
	}

	@Override
	@Transactional
	public BookBo delete(String id) {
		Book book = this.bookRepository.delete(id);
		return new BookBo(book, book.getAuthor());
	}

	@Override
	public BookBo getBook(String id) {
		Book book = bookRepository.get(id);
		if (book == null)
			throw new NullPointerException("book not found");
		return new BookBo(book, book.getAuthor());
	}

	@Override
	public ListBo<BookSearchBo> homeSearch(PageCmd page) {
		Long totalElement = bookRepository.totalCount(page);
		return totalElement == 0l ? new ListBo<>(page)
				: new ListBo<>(page.getPageNumber(), totalElement, bookRepository.homeSearch(page));

	}

	@Override
	public BookBo getPublishedBook(String id) {
		return bookRepository.getPulishedBook(id);
	}

	@Override
	public OwerOverviewBo getUserBookStoreDetails(String id) {
		return new OwerOverviewBo(10, 20);
	}

}
