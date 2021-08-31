package com.ksp.cmd;

import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageCmd {
	private int pageNumber = 0;
	private String sortOn = "dateCreated";
	private Direction sortOrder = Direction.DESC;
	private String searchValue;

	public PageCmd(String searchValue) {
		this.searchValue = searchValue;
	}

}
