package com.triconinfotech.WealthWise.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.opencsv.exceptions.CsvValidationException;
import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.entity.Timelog;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.ProjectRepository;
import com.triconinfotech.WealthWise.repository.TimeLogRepository;
import com.triconinfotech.WealthWise.service.EmployeeProjectRelationImpl;
import com.triconinfotech.WealthWise.service.ProjectServiceImpl;
import com.triconinfotech.WealthWise.service.TimeLogServiceImpl;

@SpringBootTest
public class TimesheetServiceImplTest {

	@Mock
	private TimeLogRepository timesheetRepository;

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private ProjectServiceImpl projectService;

	@Mock
	private EmployeeProjectRelationImpl eprService;

	@InjectMocks
	private TimeLogServiceImpl timelogService;

	@Test
	void mergeTimesheet_ValidTimesheet_ReturnsMergedTimesheet() {
		// Arrange
		Timelog timesheet = new Timelog();
		timesheet.setEmpId("1");
		Project project = new Project();
		project.setId(1);
		timesheet.setProjects(project);

		EmployeeProjectRelation epr = new EmployeeProjectRelation();
		epr.setEmployeeId(1);
		epr.setProjectEPR(project);

		when(projectService.getProjectById(1)).thenReturn(project);
		when(eprService.getEPRById(1)).thenReturn(epr);
		when(timesheetRepository.save(any(Timelog.class))).thenReturn(timesheet);

		// Act
		Timelog result = timelogService.mergeTimelog(timesheet);

		// Assert
		assertThat(result).isEqualTo(timesheet);
	}

	@Test
	void mergeTimesheet_NullProject_ThrowsIllegalArgumentException() {
		// Arrange
		Timelog timesheet = new Timelog();

		// Act & Assert
		CustomException exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class,
				() -> timelogService.mergeTimelog(timesheet));
		assertThat(exception.getMessage()).isEqualTo("Failed to merge timesheet");
	}

	@Test
	void getAllTimesheet_ValidOffsetAndLimit_ReturnsTimesheetList() {
		// Arrange
		when(timesheetRepository.findAllWithOffsetAndLimit(0, 10)).thenReturn(Collections.emptyList());

		// Act
		List<Timelog> result = timelogService.getAllTimelogs(1, 10);

		// Assert
		assertThat(result).isEmpty();
	}

	@Test
	void getTimesheetById_ExistingId_ReturnsTimesheet() {
		// Arrange
		Timelog timesheet = new Timelog();
		timesheet.setId(1);
		when(timesheetRepository.findById(1)).thenReturn(Optional.of(timesheet));

		// Act
		Timelog result = timelogService.getTimelogById(1);

		// Assert
		assertThat(result).isEqualTo(timesheet);
	}

	@Test
	void getTimesheetById_NonExistingId_ThrowsCustomException() {
		// Arrange
		when(timesheetRepository.findById(1)).thenReturn(Optional.empty());

		// Act & Assert
		CustomException exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class,
				() -> timelogService.getTimelogById(1));
		assertThat(exception.getMessage()).isEqualTo("Timelog not found");
		assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void deleteTimesheet_ExistingId_DeletesTimesheet() {
	    // Arrange
	    Timelog timesheet = new Timelog();
	    timesheet.setId(1);
	    timesheet.setDeletedAt(null);
	    when(timesheetRepository.findById(1)).thenReturn(Optional.of(timesheet));

	    // Act
	    timelogService.deleteTimelog(1);

	    // Assert
	    verify(timesheetRepository, times(1)).deleteById(1);
	}

	@Test
	void deleteTimesheet_NonExistingId_ThrowsCustomException() {
		// Arrange
		when(timesheetRepository.existsById(1)).thenReturn(false);

		// Act & Assert
		CustomException exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class,
				() -> timelogService.deleteTimelog(1));
		assertThat(exception.getMessage()).isEqualTo("Timelog not found");
		assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void getTimesheetByProject_ValidProjectId_ReturnsTimesheetList() {
		// Arrange
		Project project = new Project();
		project.setId(1);
		Timelog timesheet1 = new Timelog();
		timesheet1.setId(1);
		timesheet1.setProjects(project);
		Timelog timesheet2 = new Timelog();
		timesheet2.setId(2);
		timesheet2.setProjects(project);
		List<Timelog> timesheets = Arrays.asList(timesheet1, timesheet2);
		when(projectRepository.findById(1)).thenReturn(Optional.of(project));
		when(timesheetRepository.findByProjects(project)).thenReturn(timesheets);

		// Act
		List<Timelog> result = timelogService.getTimelogByProject(1);

		// Assert
		assertThat(result).hasSize(2);
		assertThat(result.get(0)).isEqualTo(timesheet1);
		assertThat(result.get(1)).isEqualTo(timesheet2);
	}

	@Test
	void generateTimesheetCSV_ValidInputs_GeneratesCSV() throws CsvValidationException {
		// Arrange
		Project project = new Project();
		project.setId(2); // Ensure this matches the project ID in the test

		Timelog timesheet1 = new Timelog();
		timesheet1.setId(1);
		timesheet1.setEmpId("1");
		timesheet1.setProjects(project);
		timesheet1.setDate(LocalDate.of(2024, 5, 15));
		timesheet1.setHours(LocalTime.of(8, 0));

		Timelog timesheet2 = new Timelog();
		timesheet2.setId(2);
		timesheet2.setEmpId("2");
		timesheet2.setProjects(project);
		timesheet2.setDate(LocalDate.of(2024, 5, 16));
		timesheet2.setHours(LocalTime.of(7, 0));

		List<Timelog> timesheets = Arrays.asList(timesheet1, timesheet2);
		when(projectRepository.findById(2)).thenReturn(Optional.of(project));
		when(timesheetRepository.findByProjects(project)).thenReturn(timesheets);

		// Act
		ResponseEntity<?> response = timelogService.generateTimelogCSV(2, 5, 2024);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().toString()).contains("CSV file written successfully: nulltimesheet.csv");
	}

	@Test
	void generateTimesheetCSV_InvalidMonth_ThrowsCustomException() {
		// Arrange
		Integer id = 1, month = 13, year = 2024;

		// Act & Assert
		CustomException exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class,
				() -> timelogService.generateTimelogCSV(id, month, year));
		assertThat(exception.getMessage()).isEqualTo("Invalid month value. Month must be between 1 and 12.");
		assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void generateNullTimesheetCSV_ValidInputs_GeneratesCSV() throws CsvValidationException {
		// Arrange
		Project project = new Project();
		project.setId(1);

		Timelog timesheet1 = new Timelog();
		timesheet1.setId(1);
		timesheet1.setEmpId("1");
		timesheet1.setProjects(project);
		timesheet1.setDate(LocalDate.of(2024, 5, 15));
		timesheet1.setHours(LocalTime.of(8, 0));

		Timelog timesheet2 = new Timelog();
		timesheet2.setId(2);
		timesheet2.setEmpId("1");
		timesheet2.setProjects(project);
		timesheet2.setDate(LocalDate.of(2024, 5, 16));
		timesheet2.setHours(null); // This will generate null entry in CSV

		List<Timelog> timesheets = Arrays.asList(timesheet1, timesheet2);
		when(projectRepository.findById(1)).thenReturn(Optional.of(project));
		when(timesheetRepository.findByProjects(project)).thenReturn(timesheets);

		// Act
		ResponseEntity<?> response = timelogService.generateTimelogCSV(1, 5, 2024);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().toString()).contains("CSV file written successfully: nulltimesheet.csv");
	}
}
