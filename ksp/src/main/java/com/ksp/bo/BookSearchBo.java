package com.ksp.bo;

import com.ksp.enums.BookStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookSearchBo {
	private String id;
	private String name;
	private int edition;
	private BookStatus status;

	public BookSearchBo(String name) {
		this.name = name;
	}

	public BookSearchBo(String id, String name, int edition, BookStatus status) {
		super();
		this.id = id;
		this.name = name;
		this.edition = edition;
		this.status = status;
	}
}
