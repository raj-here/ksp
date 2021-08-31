package com.ksp.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ksp.bo.BookBo;
import com.ksp.cmd.BookCmd;
import com.ksp.enums.BookCondition;
import com.ksp.enums.BookStatus;
import com.ksp.service.BookService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class BookServiceImplTest {
	@Autowired
	private BookService bookService;
	private String bookId;

	@DisplayName("with new Author")
	@Test
	@Order(1)
	void createBook1() {
		BookBo book = this.saveOrUpdate(null, "Author");
		this.bookId = book.getId();
		this.clear(this.bookId);
		Assert.notNull(book, () -> "The entity must not be null!");
	}

	@DisplayName("with old Author")
	@Test
	@Order(2)
	void createBook2() {
		BookBo book = this.saveOrUpdate(null, "Author");
		this.bookId = book.getId();
		this.updateBook(this.bookId);
		this.clear(this.bookId);
		Assert.notNull(book, () -> "The entity must not be null!");
	}

	@DisplayName("with no Author")
	@Test
	@Order(3)
	void createBook3() {
		BookBo book = this.saveOrUpdate(null, null);
		this.bookId = book.getId();
		this.updateBook(this.bookId);
		this.clear(this.bookId);
		Assert.notNull(book, () -> "The entity must not be null!");
	}

	void clear(String id) {
		this.bookService.delete(id);
		BookBo book = this.bookService.getBook(id);
		Assert.isNull(book, () -> "The entity must not be null!");
	}

	void updateBook(String id) {
		BookBo book = saveOrUpdate(id, "Author");
		Assert.notNull(book, () -> "The entity must not be null!");
	}

	private BookBo saveOrUpdate(String id, String author) {
		BookCmd cmd = new BookCmd("TEST Book", "Author", true, 2, "DESC", BookStatus.DONATE,
				BookCondition.NOTICEABLY_USED);
		if (StringUtils.isEmpty(id)) {
			return this.bookService.saveBook(cmd);
		} else {
			return this.bookService.updateBook(id, cmd);

		}
	}

}