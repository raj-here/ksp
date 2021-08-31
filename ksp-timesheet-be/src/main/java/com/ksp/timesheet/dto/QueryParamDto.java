package com.ksp.timesheet.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryParamDto { 
	private int number = 0;
	private int slice = 20;
	private Direction order = Direction.ASC;
	private String field = "id";

	public Pageable toPageable() {
		return PageRequest.of(number, Math.min(slice, 100), Sort.by(order, field));
	}
}