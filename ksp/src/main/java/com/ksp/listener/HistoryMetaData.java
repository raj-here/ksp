package com.ksp.listener;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoryMetaData implements Serializable {
	private static final long serialVersionUID = 9216701113110977374L;
	private String entityId;
	private boolean noHistoryRequired;

}
