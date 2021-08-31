package com.ksp.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ksp.enums.ActionType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "AUDIT")
public class Audit implements Serializable {
	private static final long serialVersionUID = -3938208473516900345L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ENTITY_ID")
	private String entityId;
	@Column(name = "ENTITY_NAME", length = 100)
	private String entityName;

	@Column(name = "FIELD_NAME", length = 50)
	private String fieldName;
	@Column(name = "TABLE_NAME", length = 50)
	private String tableName;
	@Column(name = "COLUMN_NAME", length = 50)
	private String columnName;

	@Column(name = "OLD_VALUE")
	private String oldValue;
	@Column(name = "NEW_VALUE")
	private String newValue;

	@Enumerated(EnumType.STRING)
	@Column(name = "ACTION_TAKEN", length = 40)
	private ActionType actionTaken;
	@Column(name = "ACTION_BY_ID", length = 100)
	private String actionBy;
	@Column(name = "ACTION_DATE")
	private Long actionDate;

}
