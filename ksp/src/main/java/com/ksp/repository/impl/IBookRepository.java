package com.ksp.repository.impl;

import java.util.List;

import com.ksp.bo.BookBo;
import com.ksp.bo.BookSearchBo;
import com.ksp.cmd.PageCmd;

public interface IBookRepository {
	List<BookSearchBo> homeSearch(PageCmd page);

	Long totalCount(PageCmd page);

	BookBo getPulishedBook(String id);
}
