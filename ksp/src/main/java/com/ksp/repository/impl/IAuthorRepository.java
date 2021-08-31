package com.ksp.repository.impl;

import java.util.List;

import com.ksp.bo.AuthorBo;
import com.ksp.cmd.PageCmd;
import com.ksp.domain.Author;

public interface IAuthorRepository {

	List<AuthorBo> list(PageCmd page);

	Long totalCount(PageCmd page);

	Author getByUser(String id);
}
