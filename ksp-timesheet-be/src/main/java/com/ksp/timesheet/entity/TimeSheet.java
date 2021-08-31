package com.ksp.timesheet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timesheets")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class TimeSheet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob 
	@Column(name = "task_text")
	private String task;
	
	@Column(name = "task_id", nullable = true)
	private String taskId;
	
	@Column(name = "task_link", nullable = true)
	private String taskLink;
	
	@Column(name = "task_spent", nullable = true)
	private Long timeSpent;

	@Column(name = "time_log_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeLogDate;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	private User forUser;
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;

	@Override
	public String toString() {
		return "TimeSheet [task=" + task + ", taskId=" + taskId + "]";
	}
}
