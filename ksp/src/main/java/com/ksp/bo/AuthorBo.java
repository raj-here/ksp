package com.ksp.bo;

import com.ksp.domain.Author;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorBo {

	private String id;
	@NonNull
	private String name;

	public AuthorBo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "AuthorBo [id=" + id + ", name=" + name + "]";
	}

	public AuthorBo(Author author) {
		this.id = author.getId();
		this.name = author.getName();
	}

}
