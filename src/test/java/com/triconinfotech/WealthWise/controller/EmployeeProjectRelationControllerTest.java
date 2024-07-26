package com.triconinfotech.WealthWise.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.triconinfotech.WealthWise.dto.EPRGETResponseDTO;
import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.entity.Role;
import com.triconinfotech.WealthWise.service.EmployeeProjectRelationInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * The Class EmployeeProjectRelationControllerTest.
 */
class EmployeeProjectRelationControllerTest {

	/** The employee project relation service. */
	@Mock
	EmployeeProjectRelationInterface employeeProjectRelationService;

	/** The employee project relation controller. */
	@InjectMocks
	EmployeeProjectRelationController employeeProjectRelationController;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Gets the all employee project relations should return list of employee
	 * project relations.
	 *
	 * @return the all employee project relations should return list of employee
	 *         project relations
	 */
	@Test
	void getAllEmployeeProjectRelations_shouldReturnListOfEmployeeProjectRelations() {
		// Mocking the service method to return sample employee-project relation data
		when(employeeProjectRelationService.getAllEPRRelations(anyLong(), anyLong()))
				.thenReturn(Arrays.asList(createSampleEmployeeProjectRelationEntity()));

		// Calling the controller method
		ResponseEntity<List<EPRGETResponseDTO>> responseEntity = employeeProjectRelationController
				.getAllEmployeeProjectRelations(0L, 25L);

		// Assertion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(1, responseEntity.getBody().size());
	}

	/**
	 * Gets the employee project relation by id should return employee project
	 * relation if exists.
	 *
	 * @return the employee project relation by id should return employee project
	 *         relation if exists
	 */
	@Test
	void getEmployeeProjectRelationById_shouldReturnEmployeeProjectRelationIfExists() {
		// Mocking the service method to return a sample employee-project relation
		when(employeeProjectRelationService.getEPRById(anyInt()))
				.thenReturn(createSampleEmployeeProjectRelationEntity());

		// Calling the controller method
		ResponseEntity<EPRGETResponseDTO> responseEntity = employeeProjectRelationController
				.getEmployeeProjectRelationById(1);

		// Assertion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Adds the employee project relation should return created employee project
	 * relation.
	 */
	@Test
	void addEmployeeProjectRelation_shouldReturnCreatedEmployeeProjectRelation() {
		// Mocking the service method to return a sample employee-project relation
		when(employeeProjectRelationService.mergeEPR(any())).thenReturn(createSampleEmployeeProjectRelationEntity());

		// Calling the controller method
		ResponseEntity<EmployeeProjectRelation> responseEntity = employeeProjectRelationController
				.addEmployeeProjectRelation(createSampleEmployeeProjectRelationEntity());

		// Assertion
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Update employee project relation should return updated employee project
	 * relation.
	 */
	@Test
	void updateEmployeeProjectRelation_shouldReturnUpdatedEmployeeProjectRelation() {
		// Mocking the service method to return a sample employee-project relation
		when(employeeProjectRelationService.mergeEPR(any())).thenReturn(createSampleEmployeeProjectRelationEntity());

		// Calling the controller method
		ResponseEntity<EmployeeProjectRelation> responseEntity = employeeProjectRelationController
				.updateEmployeeProjectRelation(createSampleEmployeeProjectRelationEntity(), 1);

		// Assertion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Delete employee project relation should return http status ok.
	 */
	@Test
	void deleteEmployeeProjectRelation_shouldReturnHttpStatusOk() {
		// Calling the controller method
		ResponseEntity<?> responseEntity = employeeProjectRelationController.deleteEmployeeProjectRelation(1);

		// Assertion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	/**
	 * Creates the sample employee project relation entity.
	 *
	 * @return the employee project relation
	 */
	private EmployeeProjectRelation createSampleEmployeeProjectRelationEntity() {
		EmployeeProjectRelation employeeProjectRelation = new EmployeeProjectRelation();
		employeeProjectRelation.setEmployeeId(123);
		employeeProjectRelation.setProjectEPR(createSampleProjectEntity());
	    
		return employeeProjectRelation;
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
