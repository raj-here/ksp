package com.ksp.timesheet.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ksp.timesheet.entity.TimeSheet;
import com.ksp.timesheet.repository.TimeSheetRepository;

@Service
public class TimeSheetService {

	@Autowired
	private TimeSheetRepository timeSheetepository;

	public void save(TimeSheet timeSheet) {
		timeSheetepository.save(timeSheet);
	}

	public Page<TimeSheet> getAll(Pageable page) {
		
		return timeSheetepository.findAll(page);

	}

	public List<TimeSheet> getAllByUserId(Long userId) {
		return timeSheetepository.findAllByUserId(userId);

	}

	public List<TimeSheet> getAllByTaskId(String taskId) {
		return timeSheetepository.findAllByTaskId(taskId);

	}

	public List<TimeSheet> getAllByDates(Date start, Date end) {
		return timeSheetepository.findAllByTimeLogDateBetween(start, end);

	}

	public List<TimeSheet> getAllByDates(Long userId, Date start, Date end) {
		return timeSheetepository.findAllByUserIdAndTimeLogDateBetween(userId, start, end);

	}

	public TimeSheet get(Long id) {
		Optional<TimeSheet> timeSheetOptional = timeSheetepository.findById(id);
		return timeSheetOptional.isPresent() ? timeSheetOptional.get() : null;
	}

}
