package com.ksp.bo;

import com.ksp.domain.Author;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDetailedBo extends AuthorBo {
	private String description;

	public AuthorDetailedBo(Author author) {
		super(author);
		this.description = author.getDescription();
	}

}
