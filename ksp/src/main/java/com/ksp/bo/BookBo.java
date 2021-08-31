package com.ksp.bo;

import com.ksp.domain.Author;
import com.ksp.domain.Book;
import com.ksp.enums.BookCondition;
import com.ksp.enums.BookStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookBo {
	private String id;
	private String name;
	private int edition;
	private String description;
	private BookStatus bookStatus;
	private BookCondition bookCondition;
	private AuthorBo author;

	public BookBo(String name) {
		this.name = name;
	}

	public BookBo(Book book, Author author) {
		this.id = book.getId();
		this.name = book.getName();
		this.edition = book.getEdition();
		this.description = book.getDescription();
		this.bookStatus = book.getBookStatus();
		this.bookCondition = book.getBookCondition();
		if (author != null)
			this.author = new AuthorBo(author.getId(), author.getName());
	}

	@Override
	public String toString() {
		return "BookBo [id=" + id + ", name=" + name + ", edition=" + edition + ", description="
				+ description + ", bookStatus=" + bookStatus + ", bookCondition=" + bookCondition
				+ ", author=" + author + "]";
	}

}
