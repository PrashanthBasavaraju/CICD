package com.triconinfotech.WealthWise.controller;

import com.opencsv.exceptions.CsvValidationException;

import com.triconinfotech.WealthWise.dto.ProjectGETResponseDTO;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.mapper.ProjectDTOMapper;
import com.triconinfotech.WealthWise.service.ProjectServiceImpl;
import com.triconinfotech.WealthWise.service.ProjectServiceInterface;
import com.triconinfotech.WealthWise.service.TimeLogServiceImpl;
import com.triconinfotech.WealthWise.service.TimeLogServiceInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The Class ProjectController handles HTTP requests related to project
 * management. It provides endpoints to perform CRUD operations on projects and
 * to generate Timelog reports in CSV format.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/projects")
@Tag(name = "Project")
public class ProjectController {

	/** The project service. */
	private final ProjectServiceImpl projectService;

	/** The Timelog service. */
	private final TimeLogServiceImpl timelogService;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	/**
	 * This gives the response of List of projects with the given offset and limit
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */

	@GetMapping
	public ResponseEntity<List<ProjectGETResponseDTO>> getAllProjects(
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "25") long limit) {
		if (offset < 0) {
			offset = 0;
		}

		logger.info("Fetching all projects.");
		List<ProjectGETResponseDTO> projects = ProjectDTOMapper
				.getProjectDTOList(projectService.getAllProject(offset, limit));
		return new ResponseEntity<>(projects, HttpStatus.OK);
	}

	/**
	 * This is gives the project on the particular id
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping("/{id}")
	public ResponseEntity<ProjectGETResponseDTO> getProjectById(@PathVariable Integer id) {

		logger.info("Fetching project with ID: {}", id);
		ProjectGETResponseDTO project = ProjectDTOMapper.getProjectDTO(projectService.getProjectById(id));
		return new ResponseEntity<>(project, HttpStatus.OK);

	}

	/**
	 * This is used to add the new project to the list
	 * 
	 * @param project
	 * @return
	 */

	@PostMapping
	public ResponseEntity<Project> addProject(@RequestBody Project project) {

		logger.info("Adding a new project.");
		Project createdProject = projectService.mergeProject(project);
		return new ResponseEntity<>(createdProject, HttpStatus.CREATED);

	}

	/**
	 * This is used to update the existing project based on id
	 * 
	 * @param project
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Project> updateProject(@RequestBody Project project, @PathVariable Integer id) {
		logger.info("Updating project with ID: {}", id);
		project.setId(id); // Ensure the ID is set for the update
		Project updatedProject = projectService.mergeProject(project);
		return new ResponseEntity<>(updatedProject, HttpStatus.OK);
	}

	/**
	 * This is used to delete the particular project
	 * 
	 * @param id
	 * @return
	 */

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProject(@PathVariable Integer id) {
		logger.info("Deleting project with ID: {}", id);
		projectService.deleteProject(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * 
	 * @param name
	 * @return
	 */

	@GetMapping("/search")
	public List<Project> searchProjectsByName(@RequestParam String name) {
		System.out.println("hello");
		return projectService.findByName(name);
	}

	/**
	 * This is used to fetch the invoice Timelog of the particular project based on
	 * the month and year and gives the response in stored in csv file
	 * 
	 * @param id
	 * @param month
	 * @param years
	 * @return
	 * @throws CsvValidationException
	 */

	@GetMapping("/timelog/{id}")
	public ResponseEntity<?> getTimelogAndExportCSV(@PathVariable Integer id,
			@RequestParam(name = "month") Integer month, @RequestParam(name = "year") Integer year) {
		try {
			return timelogService.generateTimelogCSV(id, month, year);

		} catch (CsvValidationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error generating CSV: " + e.getMessage());
		}
	}

	/**
	 * This is used to fetch the invoice or Timelog of the particular employee for
	 * the range of the dates provided
	 * 
	 * @param id
	 * @param month
	 * @param years
	 * @param emp_Id
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws CsvValidationException
	 */

	private final Set<String> acceptedParams = Set.of("empId", "startDate", "endDate");

	@GetMapping("/emptyTimelog/{id}")
	public ResponseEntity<?> getNullTimelogAndExportCSV(@Validated @PathVariable Integer id,
			@RequestParam(name = "empId") Integer empId,
			@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam Map<String, String> queryParams) {
		queryParams.keySet().removeAll(acceptedParams);
		if (!queryParams.isEmpty()) {
			String unexpectedParams = String.join(", ", queryParams.keySet());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected parameters: " + unexpectedParams);
		}

		try {
			return timelogService.generateNullTimelogCSV(id, empId, startDate, endDate);

		} catch (CsvValidationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error generating CSV: " + e.getMessage());
		}
	}

	@GetMapping("/client")
    public ResponseEntity<List<String>> getProjectNamesByClientId(@RequestParam Integer clientId) {
        List<String> projectNames = projectService.getProjectNamesByClientId(clientId);
        return new ResponseEntity<>(projectNames, HttpStatus.OK);
    }

}
