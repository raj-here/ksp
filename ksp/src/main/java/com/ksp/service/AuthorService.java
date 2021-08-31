package com.ksp.service;

import java.util.List;

import com.ksp.bo.AuthorBo;
import com.ksp.bo.AuthorDetailedBo;
import com.ksp.bo.ListBo;
import com.ksp.cmd.AuthorCmd;
import com.ksp.cmd.PageCmd;

public interface AuthorService {
	AuthorDetailedBo saveAuthor(AuthorCmd cmd);

	AuthorDetailedBo updateAuthor(String id, AuthorCmd cmd);

	AuthorDetailedBo get(String id);

	AuthorBo delete(String id);

	List<AuthorBo> autocompleteAuthor(String value);

	ListBo<AuthorBo> list(PageCmd page);
}
