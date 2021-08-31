package com.ksp.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "ACTIONABLES")
public class Actionable extends Auditable {
	private static final long serialVersionUID = -6142216499054357155L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ID")
	private String id;

	@Column(name = "REFERENCE_ID", length = 100)
	private String referenceId;

	@Column(name = "CLASS_NAME", length = 50)
	private String className;

}
