package com.ksp.timesheet.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeSheetDto {
	private Long id;
	private String task;
	private String taskId;
	private String taskLink;
	private Long timespent;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date timeLogDate;
	private String userId;
	@Override
	public String toString() {
		return "TimeSheet [task=" + task + ", taskId=" + taskId + "]";
	}

}