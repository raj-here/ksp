package com.ksp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ksp.domain.common.Auditable;
import com.ksp.enums.BookCondition;
import com.ksp.enums.BookStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "USER_BOOKS")
public class Book extends Auditable {
	private static final long serialVersionUID = 5739994595883561421L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "EDITION", columnDefinition = "integer default 1")
	private int edition;

	@Column(name = "DESCRIPTION", nullable = true, length = 500)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "BOOK_STATUS", length = 50)
	private BookStatus bookStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "BOOK_CONDITION", length = 50)
	private BookCondition bookCondition;

	@Column(name = "REPORTED")
	private int reported = 0;

	@ManyToOne
	@JoinColumn(name = "AUTHOR_ID")
	private Author author;

	public Book(String name, int edition, String description, BookStatus bookStatus,
			BookCondition bookCondition) {
		this.name = name;
		this.edition = edition;
		this.description = description;
		this.bookStatus = bookStatus;
		this.bookCondition = bookCondition;
	}

}
