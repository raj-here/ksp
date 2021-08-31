package com.ksp.bo;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActionBo implements Serializable {
	private static final long serialVersionUID = -2537882110339098200L;
	private String referenceId;
	private String className;

	public ActionBo(String referenceId, String className) {
		this.referenceId = referenceId;
		this.className = className;
	}

}
