package com.triconinfotech.WealthWise.service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.entity.Role;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.EmployeeProjectRelationRepository;

import lombok.AllArgsConstructor;

/**
 * The Class EmployeeProjectRelationImpl.
 */
@AllArgsConstructor
@Service
public class EmployeeProjectRelationImpl implements EmployeeProjectRelationInterface {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(EmployeeProjectRelationImpl.class);

	/** The epr relation repo. */
	private final EmployeeProjectRelationRepository eprRelationRepo;

	/** The project service. */
	private final ProjectServiceInterface projectService;

	private final RoleServiceInterface roleService;
	private static final String EPR_NOT_FOUND = "EPR not found";

	/**
	 * Merge EPR.
	 *
	 * @param EPR the epr
	 * @return the employee project relation
	 */
	@Override
	@Transactional
	public EmployeeProjectRelation mergeEPR(EmployeeProjectRelation EPR) {
		try {
			Project project = projectService.getProjectById(EPR.getProjectEPR().getId());

			if (project == null) {
				throw new CustomException("Project does not exist", HttpStatus.NOT_FOUND);
			}

			Role role = EPR.getRoletype();

			// Check if the role exists
			if (role == null || role.getId() == null) {
				throw new CustomException("Role ID is missing or invalid", HttpStatus.BAD_REQUEST);
			}

			// Check if the role ID exists in the role table
			Role existingRole = roleService.getRoleById(role.getId());
			if (existingRole == null) {
				throw new CustomException("Role with ID " + role.getId() + " does not exist", HttpStatus.NOT_FOUND);
			}
			
			
			//validate for the duplicate for employee and project
			EmployeeProjectRelation existingEPR =findByProjectAndEmployeeId(EPR.getProjectEPR(), EPR.getEmployeeId());
	        if (existingEPR != null) {
	        	System.out.println("Duplicate entry");
	            throw new CustomException("Duplicate EPR exists for the given project and employee", HttpStatus.CONFLICT);
	        }
			
			project = projectService.mergeProject(project);
			EPR.setProjectEPR(project);
			return eprRelationRepo.save(EPR);
		} catch (Exception e) {
			logger.error("Error occurred while merging EPR: {} from service implementation file", e.getMessage());
			if(e.getMessage().contains("Duplicate")) {
				throw new CustomException("Duplicate Entry", HttpStatus.CONFLICT);
			}else if(e.getMessage().contains("invalid")){
				throw new CustomException("Invalid role Id", HttpStatus.BAD_REQUEST);
			}
			else if(e.getMessage().contains("does not exists")) {
				throw new CustomException("Project does not exists", HttpStatus.NOT_FOUND);
			}
			else {
			throw new CustomException("EPR not merged", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	/**
	 * Gets the all EPR relations.
	 *
	 * @param offset the offset
	 * @param limit  the limit
	 * @return the all EPR relations
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EmployeeProjectRelation> getAllEPRRelations(long offset, long limit) {
		try {
			logger.info("Fetching all EPR relations from service implementation file");
			long adjustedOffset = offset > 0 ? offset - 1 : 0;
			return eprRelationRepo.findAllWithOffsetAndLimit(adjustedOffset, limit);
		} catch (Exception e) {
			logger.error("An error occurred while fetching all EPRs: {}", e.getMessage());
			throw new CustomException("Failed to fetch all EPRs", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<EmployeeProjectRelation> getAllEprs(long offset, long limit) throws CustomException {
		try {
			System.out.println("hello");
			logger.info("fetching all eprRelations from service file");
			return eprRelationRepo.findAll();
		} catch (Exception e) {
			logger.error("An error occurred while fetching all EPRs: {}", e.getMessage());
			throw new CustomException("Failed to fetch all EPRs", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Gets the EPR by id.
	 *
	 * @param id the id
	 * @return the EPR by id
	 * @throws CustomException the custom exception
	 */
	@Override
	@Transactional(readOnly = true)
	public EmployeeProjectRelation getEPRById(Integer id) throws CustomException {
		logger.info("Fetching EPR relation by id: {} from service implementation file", id);
		Optional<EmployeeProjectRelation> eprRelationOptional = eprRelationRepo.findById(id);
		return eprRelationOptional.filter(epr -> epr.getDeletedAt() == null).orElseThrow(() -> {
			logger.error("EPR not found or already deleted with id: {} from service implementation file", id);
			return new CustomException("EPR not found", HttpStatus.NOT_FOUND);
		});
	}

	/**
	 * Delete EPR.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	public void deleteEPR(Integer id) {
		logger.info("Deleting EPR with id: {}", id);
		Optional<EmployeeProjectRelation> eprRelationOptional = eprRelationRepo.findById(id);
		if (eprRelationOptional.isPresent()) {
			EmployeeProjectRelation epr = eprRelationOptional.get();
			if (epr.getDeletedAt() == null) {
				// Proceed with deletion
				eprRelationRepo.deleteById(id);
				logger.info("EPR with id {} deleted successfully", id);
			} else {
				logger.error("EPR with id {} is already deleted", id);
				throw new CustomException(EPR_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.error("EPR not found with id: {} from service implementation file", id);
			throw new CustomException(EPR_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	public EmployeeProjectRelation findByProjectAndEmployeeId(Project project, Integer empId) {
		logger.info("fetching timesheet based on project and employeeId");
		return eprRelationRepo.findByProjectEPRAndEmployeeId(project, empId);
	}

	public List<EmployeeProjectRelation> getEmployeeProjectRelationsByEmployeeId(Integer employeeId) {
		return eprRelationRepo.findByEmployeeId(employeeId);
	}

	public List<String> getProjectNamesByEmployeeId(Integer employeeId) {
		if (employeeId == null) {
			throw new IllegalArgumentException("Employee ID cannot be null");
		}

//		// Check if employee exists in the database using findById
//		if (!eprRelationRepo.findById(employeeId).isPresent()) {
//			logger.error("EPR not found with id: {} from service implementation file", employeeId);
//			throw new CustomException(EPR_NOT_FOUND, HttpStatus.NOT_FOUND);
//		}
		List<EmployeeProjectRelation> eprList = eprRelationRepo.findByEmployeeId(employeeId);
		return eprList.stream().map(epr -> epr.getProjectEPR().getName()).distinct().collect(Collectors.toList());
	}
}
