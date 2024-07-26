package com.triconinfotech.WealthWise.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.triconinfotech.WealthWise.dto.ProjectGETResponseDTO;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.service.ProjectServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * The Class ProjectControllerTest.
 */
class ProjectControllerTest {

	/** The project service. */
	@Mock
	ProjectServiceInterface projectService;

	/** The project controller. */
	@InjectMocks
	ProjectController projectController;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Gets the all projects should return list of projects.
	 *
	 * @return the all projects should return list of projects
	 */
	@Test
	void getAllProjects_shouldReturnListOfProjects() {

		when(projectService.getAllProject(anyLong(), anyLong())).thenReturn(Arrays.asList(createSampleProjectEntity()));

		ResponseEntity<List<ProjectGETResponseDTO>> responseEntity = projectController.getAllProjects(0L, 25L);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(1, responseEntity.getBody().size());
	}

	/**
	 * Gets the project by id should return project if exists.
	 *
	 * @return the project by id should return project if exists
	 */
	@Test
	void getProjectById_shouldReturnProjectIfExists() {

		when(projectService.getProjectById(anyInt())).thenReturn(createSampleProjectEntity());
		ResponseEntity<ProjectGETResponseDTO> responseEntity = projectController.getProjectById(1);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Adds the project should return created project.
	 */
	@Test
	void addProject_shouldReturnCreatedProject() {

		when(projectService.mergeProject(any())).thenReturn(createSampleProjectEntity());

		ResponseEntity<Project> responseEntity = projectController.addProject(createSampleProjectEntity());

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Update project should return updated project.
	 */
	@Test
	void updateProject_shouldReturnUpdatedProject() {

		when(projectService.mergeProject(any())).thenReturn(createSampleProjectEntity());

		ResponseEntity<Project> responseEntity = projectController.updateProject(createSampleProjectEntity(), 1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Delete project should return http status ok.
	 */
	@Test
	void deleteProject_shouldReturnHttpStatusOk() {

		ResponseEntity<?> responseEntity = projectController.deleteProject(1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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
