package com.triconinfotech.WealthWise.service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.triconinfotech.WealthWise.entity.Client;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.ProjectRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

/**
 * The Class ProjectServiceImpl.
 */
@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectServiceInterface {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	/** The project repo. */
	private final ProjectRepository projectRepo;

	/** The client service. */
	private final ClientServiceImpl clientService;

	private static final String PROJECT_NOT_FOUND = "Project not found";

	/**
	 * Merge project.
	 *
	 * @param project the project
	 * @return the project
	 */
	@Override
	@Transactional
	public Project mergeProject(Project project) {
		try {

			Client client = project.getClient();
			if (client.getId() == null) {
				throw new IllegalArgumentException("Client is null");
			}

			Integer clientId = client.getId();
			Client existingClient = clientService.getClientById(clientId);
			if (existingClient == null) {
				throw new IllegalArgumentException("Client with ID" + clientId + "does not exist");
			}

			project.setClient(existingClient);

			return projectRepo.save(project);
		} catch (Exception e) {
			logger.error("Error occurred while merging project: {} from service implementation file", e.getMessage());
			throw new CustomException("Project failed to merge", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Gets the all project.
	 *
	 * @param offset the offset
	 * @param limit  the limit
	 * @return the all project
	 * @throws CustomException the custom exception
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Project> getAllProject(long offset, long limit) throws CustomException {
		try {
			logger.info("Fetching all projects from service implementation file");
			long adjustedOffset = offset > 0 ? offset - 1 : 0;
			return projectRepo.findAllWithOffsetAndLimit(adjustedOffset, limit);
		} catch (Exception e) {
			logger.error("An error occurred while fetching all projects: {}", e.getMessage());
			throw new CustomException("Failed to fetch all projects", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Gets the project by id.
	 *
	 * @param pid the pid
	 * @return the project by id
	 */
	@Override
	@Transactional(readOnly = true)
	public Project getProjectById(Integer pid) {
		logger.info("Fetching project by id: {} from service implementation file", pid);
		Optional<Project> optionalProject = projectRepo.findById(pid);
		return optionalProject.filter(project -> project.getDeletedAt() == null).orElseThrow(() -> {
			logger.error("Project not found or already deleted with id: {} from service implementation file", pid);
			System.out.println("Project not found or already deleted with id: {} from service implementation file");
			throw new CustomException(PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
		});
	}

	/**
	 * Delete project.
	 *
	 * @param pid the pid
	 */
	@Override
	@Transactional
	public void deleteProject(Integer pid) {
		logger.info("Deleting project with id: {}", pid);
		Optional<Project> optionalProject = projectRepo.findById(pid);
		if (optionalProject.isPresent()) {
			Project project = optionalProject.get();
			if (project.getDeletedAt() == null) {
				// Proceed with deletion
				projectRepo.deleteById(pid);
				logger.info("Project with id {} deleted successfully", pid);
			} else {
				logger.error("Project with id {} is already deleted", pid);
				throw new CustomException(PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.error("Project not found with id: {} from service implementation file", pid);
			throw new CustomException(PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Project> findByName(String name) {
		try {
			logger.info("Fetching projects by name: {} from service implementation file", name);
			return projectRepo.findByName(name);
		} catch (Exception e) {
			logger.error("An error occurred while fetching projects by name: {}", e.getMessage());
			throw new CustomException("Failed to fetch projects by name", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<String> getProjectNamesByClientId(Integer clientId) {
        return projectRepo.findByClientId(clientId).stream()
                .map(Project::getName)
                .collect(Collectors.toList());
    }
}
