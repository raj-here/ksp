package com.ksp.controller;

import static com.ksp.utils.Constant.BLANK;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ksp.bo.AuthorBo;
import com.ksp.bo.AuthorDetailedBo;
import com.ksp.bo.ListBo;
import com.ksp.bo.OutputBo;
import com.ksp.cmd.AuthorCmd;
import com.ksp.cmd.PageCmd;
import com.ksp.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
	@Autowired
	private AuthorService authorService;

	@GetMapping(value = "/autocomplete_value")
	public OutputBo<List<AuthorBo>> autocompleteValue(
			@RequestParam(value = "a_search", required = true) String value) {
		if (StringUtils.isEmpty(value) || value.length() <= 2) {
			return new OutputBo<>(BLANK, new ArrayList<>(1));
		}
		return new OutputBo<>(BLANK, this.authorService.autocompleteAuthor(value));
	}

	@PostMapping(value = "/list")
	public OutputBo<ListBo<AuthorBo>> myAuthor(@RequestBody PageCmd page) {
		return new OutputBo<>(BLANK, this.authorService.list(page));
	}

	@PostMapping
	public OutputBo<AuthorDetailedBo> saveAuthor(@RequestBody AuthorCmd author) {
		return new OutputBo<>("Author saved successfully", this.authorService.saveAuthor(author));
	}

	@GetMapping(value = "/{id}")
	public OutputBo<AuthorDetailedBo> getAuthor(@PathVariable("id") String id) {
		return new OutputBo<>(BLANK, this.authorService.get(id));
	}

	@PutMapping(value = "/{id}")
	public OutputBo<AuthorDetailedBo> updateAuthor(@PathVariable("id") String id,
			@RequestBody AuthorCmd author) {
		return new OutputBo<>("Author updated successfully",
				this.authorService.updateAuthor(id, author));
	}

	@DeleteMapping(value = "/{id}")
	public OutputBo<AuthorBo> deleteAuthor(@PathVariable("id") String id) {
		return new OutputBo<>("Author deleted successfully", this.authorService.delete(id));
	}

}
