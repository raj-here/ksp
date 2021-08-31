package com.ksp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ksp.cmd.AuthorCmd;
import com.ksp.domain.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "USER_AUTHORS")
public class Author extends Auditable {
	private static final long serialVersionUID = -8863371408884406491L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ID")
	private String id;
	@Column(name = "NAME", length = 100)
	private String name;
	@Column(name = "DESCRIPTION", nullable = true, length = 500)
	private String description;

	public Author(String name) {
		this.name = name;
	}
	public Author(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + "]";
	}

	public Author(AuthorCmd cmd) {
		this.description = cmd.getDescription();
		this.name = cmd.getName();
	}

}
