package com.ksp.timesheet.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksp.timesheet.entity.TimeSheet;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {

	Optional<TimeSheet> findById(Long id);
	
	List<TimeSheet> findAllByUserId(Long userId);
	

	List<TimeSheet> findAllByTaskId(String taskId);

	List<TimeSheet> findAllByUserIdAndTimeLogDateBetween(Long userId, Date start, Date end);

	List<TimeSheet> findAllByTimeLogDateBetween(Date start, Date end);

}