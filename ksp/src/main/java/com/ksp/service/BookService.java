package com.ksp.service;

import com.ksp.bo.BookBo;
import com.ksp.bo.BookSearchBo;
import com.ksp.bo.ListBo;
import com.ksp.bo.OwerOverviewBo;
import com.ksp.cmd.BookCmd;
import com.ksp.cmd.PageCmd;

import lombok.NonNull;

public interface BookService {
	BookBo saveBook(BookCmd book);

	ListBo<BookBo> list();

	BookBo delete(String id);

	BookBo updateBook(@NonNull String id, BookCmd bookCmd);

	BookBo getBook(String id);

	ListBo<BookSearchBo> homeSearch(PageCmd page);

	BookBo getPublishedBook(String id);

	OwerOverviewBo getUserBookStoreDetails(String id);

}
