package com.ksp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksp.bo.AuthorBo;
import com.ksp.bo.AuthorDetailedBo;
import com.ksp.bo.ListBo;
import com.ksp.cmd.AuthorCmd;
import com.ksp.cmd.PageCmd;
import com.ksp.domain.Author;
import com.ksp.repository.AuthorRepository;
import com.ksp.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public AuthorDetailedBo saveAuthor(AuthorCmd cmd) {
		Author author = authorRepository.save(new Author(cmd));
		return new AuthorDetailedBo(author);
	}

	@Override
	public AuthorBo delete(String id) {
		Author author = authorRepository.getByUser(id);
		if (author == null)
			throw new NullPointerException("Author not found");
		authorRepository.delete(author);
		return new AuthorBo(author);
	}

	@Override
	public List<AuthorBo> autocompleteAuthor(String value) {
		return authorRepository.list(new PageCmd(value));
	}

	@Override
	public AuthorDetailedBo get(String id) {
		Author author = authorRepository.getByUser(id);
		if (author == null)
			throw new NullPointerException("Author not found");
		return new AuthorDetailedBo(author);
	}

	@Override
	public ListBo<AuthorBo> list(PageCmd page) {
		Long totalElement = authorRepository.totalCount(page);
		return totalElement == 0l ? new ListBo<>(page)
				: new ListBo<>(page.getPageNumber(), totalElement, authorRepository.list(page));
	}

	@Override
	public AuthorDetailedBo updateAuthor(String id, AuthorCmd cmd) {
		Author author = authorRepository.getByUser(id);
		if (author == null)
			throw new NullPointerException("Author not found");
		author.setName(cmd.getName());
		author.setDescription(cmd.getDescription());
		authorRepository.save(author);
		return new AuthorDetailedBo(author);
	}

}
