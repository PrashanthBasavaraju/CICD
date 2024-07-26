package com.triconinfotech.WealthWise.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.EmployeeProjectRelationRepository;
import com.triconinfotech.WealthWise.service.EmployeeProjectRelationImpl;
import com.triconinfotech.WealthWise.service.ProjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeProjectRelationImplTest {

	@Mock
	private EmployeeProjectRelationRepository eprRelationRepo;

	@Mock
	private ProjectServiceImpl projectService;

	@InjectMocks
	private EmployeeProjectRelationImpl employeeProjectRelationImpl;

	private EmployeeProjectRelation epr;
	private Project project;

	@BeforeEach
	public void setUp() {
		project = new Project();
		project.setId(1);
		project.setName("Test Project");

		epr = new EmployeeProjectRelation();
		epr.setId(1);
		epr.setProjectEPR(project);
	}

	@Test
	public void testMergeEPR() {
		when(projectService.getProjectById(anyInt())).thenReturn(project);
		when(projectService.mergeProject(any(Project.class))).thenReturn(project);
		when(eprRelationRepo.save(any(EmployeeProjectRelation.class))).thenReturn(epr);

		EmployeeProjectRelation result = employeeProjectRelationImpl.mergeEPR(epr);

		assertNotNull(result);
		assertEquals(epr.getId(), result.getId());
		verify(eprRelationRepo, times(1)).save(epr);
	}

	@Test
	public void testMergeEPR_Exception() {
		when(projectService.getProjectById(anyInt()))
				.thenThrow(new CustomException("Project not found", HttpStatus.NOT_FOUND));

		CustomException exception = assertThrows(CustomException.class, () -> {
			employeeProjectRelationImpl.mergeEPR(epr);
		});

		assertEquals("EPR not merged", exception.getMessage());
	}

	@Test
	public void testGetAllEPRRelations() {
		List<EmployeeProjectRelation> eprList = new ArrayList<>();
		eprList.add(epr);

		when(eprRelationRepo.findAllWithOffsetAndLimit(any(Long.class), any(Long.class))).thenReturn(eprList);

		List<EmployeeProjectRelation> result = employeeProjectRelationImpl.getAllEPRRelations(0, 10);

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(eprRelationRepo, times(1)).findAllWithOffsetAndLimit(0L, 10L);
	}

	@Test
	public void testGetAllEPRRelations_Exception() {
		when(eprRelationRepo.findAllWithOffsetAndLimit(any(Long.class), any(Long.class)))
				.thenThrow(new RuntimeException("Database error"));

		CustomException exception = assertThrows(CustomException.class, () -> {
			employeeProjectRelationImpl.getAllEPRRelations(0, 10);
		});

		assertEquals("Failed to fetch all EPRs", exception.getMessage());
	}

	@Test
	public void testGetEPRById() {
		when(eprRelationRepo.findById(anyInt())).thenReturn(Optional.of(epr));

		EmployeeProjectRelation result = employeeProjectRelationImpl.getEPRById(1);

		assertNotNull(result);
		assertEquals(epr.getId(), result.getId());
		verify(eprRelationRepo, times(1)).findById(1);
	}

	@Test
	public void testGetEPRById_NotFound() {
		when(eprRelationRepo.findById(anyInt())).thenReturn(Optional.empty());

		CustomException exception = assertThrows(CustomException.class, () -> {
			employeeProjectRelationImpl.getEPRById(1);
		});

		assertEquals("EPR not found", exception.getMessage());
	}

	@Test
	public void testDeleteEPR() {
		when(eprRelationRepo.findById(anyInt())).thenReturn(Optional.of(epr));
		doNothing().when(eprRelationRepo).deleteById(anyInt());

		employeeProjectRelationImpl.deleteEPR(1);

		verify(eprRelationRepo, times(1)).deleteById(1);
	}

	@Test
	public void testDeleteEPR_NotFound() {
		when(eprRelationRepo.findById(anyInt())).thenReturn(Optional.empty());

		CustomException exception = assertThrows(CustomException.class, () -> {
			employeeProjectRelationImpl.deleteEPR(1);
		});

		assertEquals("EPR not found", exception.getMessage());
	}
}
