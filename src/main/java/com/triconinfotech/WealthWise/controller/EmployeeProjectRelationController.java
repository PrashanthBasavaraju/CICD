package com.triconinfotech.WealthWise.controller;

import com.triconinfotech.WealthWise.dto.EPRGETResponseDTO;
import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.mapper.EPRDTOMapper;
import com.triconinfotech.WealthWise.service.EmployeeProjectRelationImpl;
import com.triconinfotech.WealthWise.service.EmployeeProjectRelationInterface;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Class EmployeeProjectRelationController. This controller provides
 * endpoints for managing employee-project relations.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/employee-project-relations")
@Tag(name = "Employee Project Relation")
public class EmployeeProjectRelationController {

	/** The employee-project-relation service. */

	private final EmployeeProjectRelationImpl eprService;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeProjectRelationController.class);

	/**
	 * Gets the all employee-project-relations.
	 *
	 * @param offset the offset for pagination
	 * @param limit  the limit for pagination
	 * @return the all employee-project-relations
	 */
	@GetMapping
	public ResponseEntity<List<EPRGETResponseDTO>> getAllEmployeeProjectRelations(
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "25") long limit) {
		if (offset < 0) {
			offset = 0;
		}

		System.out.println("hello");

		logger.info("Fetching all employee project relations.");
		List<EPRGETResponseDTO> eprs = EPRDTOMapper
				.getEPRGetResponseDTOList(eprService.getAllEPRRelations(offset, limit));
		return new ResponseEntity<>(eprs, HttpStatus.OK);
	}


	/**
	 * Gets the employee-project-relation by id.
	 *
	 * @param id the id of the employee-project-relation
	 * @return the employee-project-relation by id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<EPRGETResponseDTO> getEmployeeProjectRelationById(@PathVariable Integer id) {
		logger.info("Fetching employee project relation with ID: {}", id);
		EPRGETResponseDTO epr = EPRDTOMapper.getEPRGetResponseDTO(eprService.getEPRById(id));
		return new ResponseEntity<>(epr, HttpStatus.OK);
	}

	/**
	 * Adds the employee-project-relation.
	 *
	 * @param employeeProjectRelation the employee-project-relation entity
	 * @return the response entity containing the created employee-project-relation
	 */
	@PostMapping
	public ResponseEntity<EmployeeProjectRelation> addEmployeeProjectRelation(
			@RequestBody EmployeeProjectRelation employeeProjectRelation) {
		logger.info("Adding a new employee project relation.");
		EmployeeProjectRelation createdEPR = eprService.mergeEPR(employeeProjectRelation);
		return new ResponseEntity<>(createdEPR, HttpStatus.CREATED);

	}

	/**
	 * Update employee-project-relation.
	 *
	 * @param employeeProjectRelation the employeeproject relation entity
	 * @param id                      the id of the employee-project relation to
	 *                                update
	 * @return the response entity containing the updated employee-project-relation
	 */
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeProjectRelation> updateEmployeeProjectRelation(
			@RequestBody EmployeeProjectRelation employeeProjectRelation, @PathVariable Integer id) {

		logger.info("Updating employee project relation with ID: {}", id);
		employeeProjectRelation.setId(id); // Ensure the ID is set for the update
		EmployeeProjectRelation updatedEPR = eprService.mergeEPR(employeeProjectRelation);
		return new ResponseEntity<>(updatedEPR, HttpStatus.OK);

	}

	/**
	 * Delete employee-project-relation.
	 *
	 * @param id the id of the employee-project-relation to delete
	 * @return the response entity
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployeeProjectRelation(@PathVariable Integer id) {
		logger.info("Deleting employee project relation with ID: {}", id);
		eprService.deleteEPR(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * 
	 * @param employeeId :UserId of the particular employee
	 * @return the Employee Project mapping for that particular userid
	 */
//	@GetMapping("/employee")
//	public ResponseEntity<List<EmployeeProjectRelation>> getEmployeeProjectRelationsByEmployeeId(
//			@RequestParam("employeeId") Integer employeeId) {
//		System.out.println("hello epr0");
//		List<EmployeeProjectRelation> relations = eprService.getEmployeeProjectRelationsByEmployeeId(employeeId);
//		return new ResponseEntity<>(relations, HttpStatus.OK);
//	}
	/**
	 * 
	 * @param employeeId: UserId of the particular employee 
	 * @return all the project the employee is working on
	 */
	
	@GetMapping("/projects")
    public ResponseEntity<List<String>> getProjectNamesByEmployeeId(@RequestParam("employeeId") Integer employeeId) {
        logger.info("Fetching project names for employee with ID: {}", employeeId);
        List<String> projectNames = eprService.getProjectNamesByEmployeeId(employeeId);
        return new ResponseEntity<>(projectNames, HttpStatus.OK);
    }
}
