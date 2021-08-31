package com.ksp.cmd;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ksp.enums.BookCondition;
import com.ksp.enums.BookStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookCmd {
	@NotNull
	@NotBlank
	@Max(100)
	private String name;
	private String author;
	private boolean isNewAuthors;
	private int edition;
	@Max(500)
	private String description;
	private BookStatus bookStatus;
	private BookCondition bookCondition;

	public BookCmd(@NotNull @NotBlank @Size(max = 100) String name, String author,
			boolean isNewAuthors, int edition, @Size(max = 500) String description,
			BookStatus bookStatus, BookCondition bookCondition) {
		this.name = name;
		this.author = author;
		this.isNewAuthors = isNewAuthors;
		this.edition = edition;
		this.description = description;
		this.bookStatus = bookStatus;
		this.bookCondition = bookCondition;
	}

}
