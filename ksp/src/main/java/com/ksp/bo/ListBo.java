package com.ksp.bo;

import java.util.ArrayList;
import java.util.List;

import com.ksp.cmd.PageCmd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListBo<T> {
	public static final int DEFAULT_PAGE_SIZE = 10;
	// TODO
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int pageNumber;
	private long totalCount = 0;
	private List<T> data;

	public ListBo(PageCmd cmd) {
		this.pageNumber = cmd.getPageNumber();
		this.data = new ArrayList<>();
	}

	public ListBo(int pageNumber, long totalCount, List<T> data) {
		super();
		this.pageNumber = pageNumber;
		this.totalCount = totalCount;
		this.data = data;
	}
}
