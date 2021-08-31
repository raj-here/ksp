package com.ksp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksp.bo.BookBo;
import com.ksp.bo.BookSearchBo;
import com.ksp.bo.ListBo;
import com.ksp.bo.OutputBo;
import com.ksp.bo.OwerOverviewBo;
import com.ksp.cmd.PageCmd;
import com.ksp.service.BookService;

@RestController
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private BookService bookService;

	@PostMapping(value = "")
	public OutputBo<ListBo<BookSearchBo>> search(@RequestBody PageCmd page) {
		return new OutputBo<>("", this.bookService.homeSearch(page));
	}

	@GetMapping(value = "/book/{id}")
	public OutputBo<BookBo> getBook(@PathVariable("id") String id) {
		return new OutputBo<>("", null);
	}

	@GetMapping(value = "/ower-overview/{id}")
	public OutputBo<OwerOverviewBo> getOwerOverview(@PathVariable("id") String id) {
		return new OutputBo<>("", this.bookService.getUserBookStoreDetails(id));
	}
}
