package com.triconinfotech.WealthWise.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import com.triconinfotech.WealthWise.controller.TimeLogController;
import com.triconinfotech.WealthWise.dto.TimeLogGETResponseDTO;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.entity.Timelog;
import com.triconinfotech.WealthWise.service.TimeLogServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class TimelogControllerTest.
 */
class TimelogControllerTest {

	/** The timelog service. */
	@Mock
	TimeLogServiceInterface timelogService;

	/** The timelog controller. */
	@InjectMocks
	TimeLogController timelogController;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Gets the all Timelogs should return list of Timelogs.
	 *
	 * @return the all Timelogs should return list of Timelogs
	 */
	@Test
	void getAllTimelogs_shouldReturnListOfTimelogs() {

		when(timelogService.getAllTimelogs(anyLong(), anyLong()))
				.thenReturn(Arrays.asList(createSampleTimelogEntity()));

		ResponseEntity<List<Timelog>> responseEntity = timelogController.getAllTimelogs(0L, 25L);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(1, responseEntity.getBody().size());
	}

	/**
	 * Gets the Timelog by id should return Timelog if exists.
	 *
	 * @return the Timelog by id should return Timelog if exists
	 */
	@Test
	void getTimelogById_shouldReturnTimelogIfExists() {

		when(timelogService.getTimelogById(anyInt())).thenReturn(createSampleTimelogEntity());

		ResponseEntity<Timelog> responseEntity = timelogController.getTimelogById(1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Adds the Timelog should return created Timelog.
	 */
	@Test
	void addTimelog_shouldReturnCreatedTimelog() {

		when(timelogService.mergeTimelog(any())).thenReturn(createSampleTimelogEntity());

		ResponseEntity<Timelog> responseEntity = timelogController.addTimelog(createSampleTimelogEntity());

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Update Timelog should return updated Timelog.
	 */
	@Test
	void updateTimelog_shouldReturnUpdatedTimelog() {

		when(timelogService.mergeTimelog(any())).thenReturn(createSampleTimelogEntity());

		ResponseEntity<Timelog> responseEntity = timelogController.updateTimelog(createSampleTimelogEntity(),
				1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Delete Timelog should return http status ok.
	 */
	@Test
	void deleteTimelog_shouldReturnHttpStatusOk() {

		ResponseEntity<?> responseEntity = timelogController.deleteTimelog(1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	/**
	 * Creates the sample Timelog entity.
	 *
	 * @return the Timelog
	 */
	private Timelog createSampleTimelogEntity() {
		Timelog Timelog = new Timelog();
		Timelog.setStartTime(LocalTime.of(9, 0));
		Timelog.setHours(LocalTime.of(8, 0));
		Timelog.setDescription("This is a sample Timelog");
		Timelog.setDate(LocalDate.of(2024, 4, 1));
		Timelog.setProjects(createSampleProjectEntity());
		Timelog.setEmpId("EMP123");

		return Timelog;
	}

	/**
	 * Creates the sample project entity.
	 *
	 * @return the project
	 */
	private Project createSampleProjectEntity() {
		Project project = new Project();
		project.setName("Sample Project");
		project.setDescription("This is a sample project");
		project.setStartDate(LocalDate.of(2024, 4, 1));
		project.setEndDate(LocalDate.of(2024, 9, 30));

		return project;
	}
}
