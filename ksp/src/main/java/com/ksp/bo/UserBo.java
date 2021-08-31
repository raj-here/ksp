package com.ksp.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBo {
	private String id;
	private String name;

	public UserBo(String name) {
		this.name = name;
	}
}
