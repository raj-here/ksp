package com.ksp.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutputBo<R> {
	private String message;
	private R record;

	public OutputBo(String message, R record) {
		this.record = record;
		this.message = message;
	}

}
