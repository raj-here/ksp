package com.ksp.domain.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.ksp.domain.common.Actionable;
import com.ksp.domain.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "COMMENTS")
public class Comment extends Auditable {
	private static final long serialVersionUID = 6432678388490624147L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ID")
	private String id;
	@Column(name = "MESSAGE")
	private String message;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ACTION_ID")
	private Actionable commentable;
}
