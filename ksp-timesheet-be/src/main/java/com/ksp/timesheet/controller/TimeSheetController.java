package com.ksp.timesheet.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksp.timesheet.dto.ListDto;
import com.ksp.timesheet.dto.QueryParamDto;
import com.ksp.timesheet.dto.TimeSheetDto;
import com.ksp.timesheet.entity.TimeSheet;
import com.ksp.timesheet.service.TimeSheetService;

/**
 * 
 * @author ashu8006kumar
 *
 */
@RestController
@RequestMapping("/api/v1/ts")
public class TimeSheetController {

	@Autowired
	private TimeSheetService timeSheetService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = { "/", "" })
	public ResponseEntity<TimeSheetDto> save(@RequestBody TimeSheetDto timeSheetDto) {
		TimeSheet ts = this.modelMapper.map(timeSheetDto, TimeSheet.class);
		ts.setUserId(null);// set current user id
		ts.setUserId(1l);
		timeSheetService.save(ts);
		return new ResponseEntity<>(this.modelMapper.map(ts, TimeSheetDto.class), HttpStatus.OK);
	}

	@GetMapping(value = { "/", "" }, params = { "number", "slice", "order", "field" })
	public ResponseEntity<ListDto<TimeSheetDto>> getAll(QueryParamDto queryParam) {
		Page<TimeSheet> timeSheetsPage = timeSheetService.getAll(queryParam.toPageable());
		ListDto<TimeSheetDto> listDto = new ListDto<>(timeSheetsPage, defaultMapper);
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@GetMapping(value = "/my")
	public ResponseEntity<ListDto<TimeSheetDto>> myTimeSheet() {
		Long currentUserId = 1l;
		// TODO change above Later
		List<TimeSheet> timeSheets = timeSheetService.getAllByUserId(currentUserId);
		ListDto<TimeSheetDto> listDto = new ListDto<>(
				timeSheets.stream().map(defaultMapper).collect(Collectors.toList()), timeSheets.size());
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@GetMapping(value = "/task-id/{taskId}")
	public ResponseEntity<ListDto<TimeSheetDto>> byTaskId(@PathParam("taskId") String taskId) {
		List<TimeSheet> timeSheets = timeSheetService.getAllByTaskId(taskId);
		ListDto<TimeSheetDto> listDto = new ListDto<>(
				timeSheets.stream().map(defaultMapper).collect(Collectors.toList()), timeSheets.size());
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@GetMapping(value = "/date-range{startDate}/{endDate}")
	public ResponseEntity<ListDto<TimeSheetDto>> dateFileter(@DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
			@DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
		List<TimeSheet> timeSheets = timeSheetService.getAllByDates(startDate, endDate);
		ListDto<TimeSheetDto> listDto = new ListDto<>(
				timeSheets.stream().map(defaultMapper).collect(Collectors.toList()), timeSheets.size());
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TimeSheetDto> get(@PathVariable(value = "id") Long id) {
		TimeSheet timeSheet = timeSheetService.get(id);
		return new ResponseEntity<>(this.modelMapper.map(timeSheet, TimeSheetDto.class), HttpStatus.OK);
	}

	private Function<TimeSheet, TimeSheetDto> defaultMapper = t -> this.modelMapper.map(t, TimeSheetDto.class);

}