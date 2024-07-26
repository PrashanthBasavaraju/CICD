package com.triconinfotech.WealthWise.service.impl;

import com.triconinfotech.WealthWise.entity.Client;

import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.ProjectRepository;
import com.triconinfotech.WealthWise.service.ClientServiceImpl;
import com.triconinfotech.WealthWise.service.ProjectServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectServiceImplTest.
 */
class ProjectServiceImplTest {

	/** The project repository. */
	@Mock
	private ProjectRepository projectRepository;

	/** The client service. */
	@Mock
	private ClientServiceImpl clientService;

	/** The project service. */
	@InjectMocks
	private ProjectServiceImpl projectService;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test merge project.
	 */
	@Test
	void testMergeProject() {

		Client client = new Client();
		client.setId(1);
		Project project = new Project();
		project.setClient(client);

		when(clientService.getClientById(1)).thenReturn(client);
		when(projectRepository.save(project)).thenReturn(project);

		Project mergedProject = projectService.mergeProject(project);

		assertNotNull(mergedProject);
		assertEquals(client, mergedProject.getClient());
		verify(projectRepository, times(1)).save(project);
	}

	/**
	 * Test merge project without client.
	 */
	@Test
	void testMergeProjectWithoutClient() {

		assertThrows(CustomException.class, () -> projectService.mergeProject(new Project()));
	}

	/**
	 * Test merge project with non existent client.
	 */
	@Test
	void testMergeProjectWithNonExistentClient() {

		Project project = new Project();
		Client client = new Client();
		client.setId(1);
		project.setClient(client);

		when(clientService.getClientById(1)).thenReturn(null);

		assertThrows(CustomException.class, () -> projectService.mergeProject(project));
	}

	/**
	 * Test get all project.
	 */
	@Test
	void testGetAllProject() {

		when(projectRepository.findAllWithOffsetAndLimit(0, 10)).thenReturn(Collections.emptyList());

		List<Project> projects = projectService.getAllProject(1, 10);

		assertNotNull(projects);
		assertEquals(0, projects.size());
		verify(projectRepository, times(1)).findAllWithOffsetAndLimit(0, 10);
	}

	/**
	 * Test get project by id.
	 */
	@Test
	void testGetProjectById() {

		Project project = new Project();
		project.setId(1);
		when(projectRepository.findById(1)).thenReturn(Optional.of(project));

		Project fetchedProject = projectService.getProjectById(1);

		assertNotNull(fetchedProject);
		assertEquals(project.getId(), fetchedProject.getId());
		verify(projectRepository, times(1)).findById(1);
	}

	/**
	 * Test get non existent project by id.
	 */
	@Test
	void testGetNonExistentProjectById() {

		when(projectRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(CustomException.class, () -> projectService.getProjectById(1));
	}

	/**
	 * Test delete project.
	 */
	@Test
	void testDeleteProject() {
		Project project = new Project();
		project.setId(1);
		when(projectRepository.findById(1)).thenReturn(Optional.of(project));

		projectService.deleteProject(1);

		verify(projectRepository, times(1)).deleteById(1);
	}

	/**
	 * Test delete non existent project.
	 */
	@Test
	void testDeleteNonExistentProject() {

		when(projectRepository.existsById(1)).thenReturn(false);

		assertThrows(CustomException.class, () -> projectService.deleteProject(1));
	}
}
