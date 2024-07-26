package com.triconinfotech.WealthWise.mapper;

import com.triconinfotech.WealthWise.dto.EPRGETResponseDTO;
import com.triconinfotech.WealthWise.dto.ProjectGETResponseDTO;
import com.triconinfotech.WealthWise.dto.ProjectPOSTAndPUTRequestDTO;
import com.triconinfotech.WealthWise.dto.ProjectPOSTAndPUTResponseDTO;
import com.triconinfotech.WealthWise.dto.TimeLogGETResponseDTO;
import com.triconinfotech.WealthWise.entity.Project;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * ProjectDTOMapper is responsible for mapping Project entities to DTOs and vice
 * versa.
 */
@Component
public class ProjectDTOMapper {

	/** The API endpoint for projects. */
	private static String API_ENDPOINT;

	/**
	 * Sets the API endpoint for projects.
	 *
	 * @param apiEndpoint the new API endpoint for projects
	 */
	// Non-static method to initialize the static field
	@Value("${api.endpoint}")
	public static void setApiEndpoint(String apiEndpoint) {
		API_ENDPOINT = apiEndpoint + "/api/projects/";

	}

	/**
	 * Converts a Project entity to a ProjectGETResponseDTO.
	 *
	 * @param project the Project entity
	 * @return the corresponding ProjectGETResponseDTO
	 */
	public static ProjectGETResponseDTO getProjectDTO(Project project) {
		List<EPRGETResponseDTO> eprDTOs = project.getEmployeeProjectRelations() != null
				? EPRDTOMapper.getEPRGetResponseDTOList(project.getEmployeeProjectRelations())
				: Collections.emptyList();

		List<TimeLogGETResponseDTO> timesheetDTOs = project.getTimelogs() != null
				? TimeLogDTOMapper.getTimesheetGetResponseDTOList(project.getTimelogs())
				: Collections.emptyList();

		return new ProjectGETResponseDTO(project.getId(),project.getName(), project.getDescription(), project.getStartDate(),
				project.getEndDate(), API_ENDPOINT + project.getId(), eprDTOs, timesheetDTOs);
//				EPRDTOMapper.getEPRGetResponseDTOList(project.getEmployeeProjectRelations()),
//				TimeLogDTOMapper.getTimesheetGetResponseDTOList(project.getTimesheets()));
	}

	/**
	 * Converts a list of Project entities to a list of ProjectGETResponseDTOs.
	 *
	 * @param projectList the list of Project entities
	 * @return the corresponding list of ProjectGETResponseDTOs
	 */
	public static List<ProjectGETResponseDTO> getProjectDTOList(List<Project> projectList) {
		return projectList.stream().map(ProjectDTOMapper::getProjectDTO).collect(Collectors.toList());
	}

	/**
	 * Converts a ProjectPOSTAndPUTRequestDTO to a Project entity.
	 *
	 * @param postRequestDTO the ProjectPOSTAndPUTRequestDTO
	 * @param id             the ID of the project
	 * @return the corresponding Project entity
	 */
	public static Project getProject(ProjectPOSTAndPUTRequestDTO postRequestDTO, Integer id) {
		Project project = new Project();
		project.setId(id);
		project.setName(postRequestDTO.name());
		project.setDescription(postRequestDTO.description());
		project.setStartDate(postRequestDTO.start_date());
		project.setEndDate(postRequestDTO.end_date());

		return project;
	}

	/**
	 * Converts a Project entity to a ProjectPOSTAndPUTResponseDTO.
	 *
	 * @param project the Project entity
	 * @return the corresponding ProjectPOSTAndPUTResponseDTO
	 */
	public static ProjectPOSTAndPUTResponseDTO getProjectPOSTAndPUTResponseDTO(Project project) {
		return new ProjectPOSTAndPUTResponseDTO(project.getName(), project.getDescription(), project.getStartDate(),
				project.getEndDate(), API_ENDPOINT + project.getId());
	}
	
	public static List<ProjectPOSTAndPUTResponseDTO> getProjectPOSTAndPUTResponseDTOs(List<Project> projectResponseList) {
		return projectResponseList
				.stream()
				.map(ProjectDTOMapper::getProjectPOSTAndPUTResponseDTO)
				.collect(Collectors.toList());
	}

}
