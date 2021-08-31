package com.ksp.cmd;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorCmd {
	@NotNull
	@NotBlank
	@Size(max = 100)
	private String name;
	@Max(500)
	private String description;

}
