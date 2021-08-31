package com.ksp.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.ksp.listener.HistoryMetaData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
//@EntityListeners(Auditor.class)
public class Auditable implements Serializable {
	private static final long serialVersionUID = -5072238427738910346L;

	@Column(name = "CREATED_BY_ID", nullable = true, length = 100)
	private String createdBy;

	@Column(name = "DATE_CREATED")
	private Long dateCreated;

	@Column(name = "UPDATED_BY_ID", nullable = true, length = 100)
	private String updatedBy;

	@Column(name = "LAST_UPDATED", nullable = true)
	private Long lastUpdated;

	@Column(name = "DELETED_ON", nullable = true)
	private Long deletedOn;

	@Transient
	private HistoryMetaData history = new HistoryMetaData();

}
