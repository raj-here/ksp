package com.ksp.controller;

import static com.ksp.utils.Constant.BLANK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksp.bo.BookBo;
import com.ksp.bo.ListBo;
import com.ksp.bo.OutputBo;
import com.ksp.cmd.BookCmd;
import com.ksp.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@GetMapping
	public OutputBo<ListBo<BookBo>> myBooks() {
		return new OutputBo<>(BLANK, this.bookService.list());
	}

	@PostMapping
	public OutputBo<BookBo> saveBook(@RequestBody BookCmd cmd) {
		return new OutputBo<>("Book saved successfully", this.bookService.saveBook(cmd));
	}

	@GetMapping(value = "/{id}")
	public OutputBo<BookBo> getBook(@PathVariable("id") String id) {
		return new OutputBo<>(BLANK, this.bookService.getBook(id));
	}

	@PutMapping(value = "/{id}")
	public OutputBo<BookBo> updateBook(@PathVariable("id") String id, @RequestBody BookCmd cmd) {
		return new OutputBo<>("Book updated successfully", this.bookService.updateBook(id, cmd));
	}

	@DeleteMapping(value = "/{id}")
	public OutputBo<BookBo> deleteBook(@PathVariable("id") String id) {
		return new OutputBo<>("Book deleted successfully", this.bookService.delete(id));
	}

}
