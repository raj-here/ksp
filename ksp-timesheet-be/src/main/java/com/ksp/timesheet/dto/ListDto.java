package com.ksp.timesheet.dto;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDto<R> {
	Collection<R> records;
	Number totalRecords;

	public ListDto(Page<R> records) {
		this.records = records.getContent();
		this.totalRecords = records.getTotalElements();
	}

	public ListDto(Collection<R> records, Number totalRecords) {
		this.records = records;
		this.totalRecords = totalRecords;
	}

	public <T> ListDto(Page<T> page,Function<T,  R> mapper) {
		this.records=page.get().map(mapper).collect(Collectors.toList());
		this.totalRecords=page.getTotalElements();
	}
}
