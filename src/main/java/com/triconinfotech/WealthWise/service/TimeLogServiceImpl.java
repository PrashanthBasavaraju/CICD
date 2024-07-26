package com.triconinfotech.WealthWise.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.exceptions.CsvValidationException;
import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;
import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.entity.Role;
import com.triconinfotech.WealthWise.entity.Timelog;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.ProjectRepository;
import com.triconinfotech.WealthWise.repository.TimeLogRepository;

import lombok.AllArgsConstructor;

/**
 * The Class TimeLogServiceImpl.
 */
@AllArgsConstructor
@Service
public class TimeLogServiceImpl implements TimeLogServiceInterface {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(TimeLogServiceImpl.class);

	/** The timesheet repo. */
	private final TimeLogRepository timelogRepo;

	/** The project service. */
	private final ProjectServiceInterface projectService;

	/** The epr service. */
	private final EmployeeProjectRelationInterface eprService;

	private final EmployeeProjectRelationImpl eprImpl;

	/** The project repo. */
	@Autowired
	private ProjectRepository projectRepo;

	private static final String TIMESHEET_NOT_FOUND = "Timelog not found";

	/**
	 * Merge timesheet.
	 *
	 * @param timesheets the timesheets
	 * @return the timesheet
	 */
	@Override
	@Transactional
	public Timelog mergeTimelog(Timelog timesheet) {
		try {
			Project project = timesheet.getProjects();

			if (project == null) {
				throw new IllegalArgumentException("Project is null in the timesheet");
			}

			Integer projectId = project.getId();
			String empId = timesheet.getEmpId();
			Integer empIdInt;

			try {
				empIdInt = Integer.parseInt(empId);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Employee ID " + empId + " is not a valid integer", e);
			}

			Project existingProject = projectService.getProjectById(projectId);
			if (existingProject == null) {
				throw new IllegalArgumentException("Project with ID " + projectId + " does not exist");
			}

			EmployeeProjectRelation epr = eprImpl.findByProjectAndEmployeeId(existingProject, empIdInt);
			if (epr == null) {
				throw new IllegalArgumentException(
						"No EmployeeProjectRelation found for project ID " + projectId + " and employee ID " + empId);
			}
			System.out.println(epr.getId());
			System.out.println(epr.getRoletype());

			timesheet.setProjects(existingProject);

			return timelogRepo.save(timesheet);

		} catch (Exception e) {
			System.out.println(e);
			logger.error("Error occurred while merging timesheet: {}", e.getMessage(), e);
			throw new CustomException("Failed to merge timesheet", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Gets the all timesheet.
	 *
	 * @param offset the offset
	 * @param limit  the limit
	 * @return the all timesheet
	 * @throws CustomException the custom exception
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Timelog> getAllTimelogs(long offset, long limit) throws CustomException {
		logger.info("Fetching all timesheets from service implementation file");
		long adjustedOffset = offset > 0 ? offset - 1 : 0;
		return timelogRepo.findAllWithOffsetAndLimit(adjustedOffset, limit);
	}

	/**
	 * Gets the timesheet by id.
	 *
	 * @param id the id
	 * @return the timesheet by id
	 * @throws CustomException the custom exception
	 */
	@Override
	@Transactional(readOnly = true)
	public Timelog getTimelogById(Integer id) throws CustomException {
		logger.info("Fetching timesheet by id: {} from service implementation file", id);
		Optional<Timelog> timesheetOptional = timelogRepo.findById(id);
		return timesheetOptional.filter(timesheet -> timesheet.getDeletedAt() == null).orElseThrow(() -> {
			logger.error("Timelog not found or already deleted with id: {} from service implementation file", id);
			return new CustomException(TIMESHEET_NOT_FOUND, HttpStatus.NOT_FOUND);
		});
	}

//	@Override
//	@Transactional(readOnly = true)
//	public Timelog getTimesheetById(Integer id) throws CustomException {
//	    logger.info("Fetching timesheet by id: {} from service implementation file", id);
//	    Optional<Timelog> timesheetOptional = timesheetRepo.findById(id);
//	    return timesheetOptional.filter(timesheet -> timesheet.getDeletedAt() == null)
//	                            .orElseThrow(() -> {
//	                                logger.error("Timelog not found or already deleted with id: {} from service implementation file", id);
//	                                return new CustomException("Timelog not found", HttpStatus.NOT_FOUND);
//	                            });
//	}

	/**
	 * Delete timesheet.
	 *
	 * @param id the id of the timesheet to delete
	 */
	@Override
	@Transactional
	public void deleteTimelog(Integer id) {
		logger.info("Deleting timesheet with id: {}", id);
		Optional<Timelog> timesheetOptional = timelogRepo.findById(id);
		if (timesheetOptional.isPresent()) {
			Timelog timesheet = timesheetOptional.get();
			if (timesheet.getDeletedAt() == null) {
				// Proceed with deletion
				timelogRepo.deleteById(id);
				logger.info("Timelog with id {} deleted successfully", id);
			} else {
				logger.error("Timelog with id {} is already deleted", id);
				throw new CustomException(TIMESHEET_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.error("Timelog not found with id: {} from service implementation file", id);
			throw new CustomException(TIMESHEET_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Gets the timesheet by project.
	 *
	 * @param projectId the project id
	 * @return the timesheet by project
	 */

	@Override
	@Transactional
	public List<Timelog> getTimelogByProject(Integer projectId) {
		logger.info("Fetching timesheets for project ID: {}", projectId);

		try {
			// Retrieve the Project object corresponding to the given projectId
			Project project = projectRepo.findById(projectId).orElseThrow(() -> {
				logger.error("Invalid project ID: {}", projectId);
				return new CustomException("Invalid project ID", HttpStatus.BAD_REQUEST);
			});

			// Return timesheets associated with the retrieved Project object
			return timelogRepo.findByProjects(project);
		} catch (CustomException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error occurred while fetching timesheets for project ID: {}: {}", projectId, e.getMessage());
			throw new CustomException("Failed to fetch timesheets for the project", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<Timelog> getTimelogByProjectAndEmployee(Integer projectId, Integer employeeId) {
		logger.info("fetching the timesheet based on project nad employee");
		try {
			Project optionalProject = projectService.getProjectById(projectId);
			if (optionalProject != null) {

				// Retrieve EmployeeProjectRelation by Project and employeeId
				EmployeeProjectRelation eprProject = eprImpl.findByProjectAndEmployeeId(optionalProject, employeeId);
				if (eprProject != null) {
					Project associatedProject = eprProject.getProjectEPR();
					Integer id = eprProject.getId();
					Integer emp_id = eprProject.getEmployeeId();
					Project project = eprProject.getProjectEPR();
					Integer eprId = eprProject.getId();
					EmployeeProjectRelation epr = eprService.getEPRById(eprId);
					Role role = epr.getRoletype();
//					List<Timelog> timesheets = timesheetRepo.findByProjects(associatedProject);
//					Timelog index = timesheets.get(0);
//					System.out.println(index.getDate());
					if (role.getId() == 1) {
						return timelogRepo.findByProjects(associatedProject);
					} else {
						Project projects = projectRepo.findById(employeeId).orElseThrow(() -> {
							logger.error("Invalid employee ID: {}", projectId);
							return new CustomException("Invalid employee ID", HttpStatus.BAD_REQUEST);
						});

						return timelogRepo.findByProjects(projects);
					}

					// Fetch timesheets by associated Project

				} else {
					// Handle case where EmployeeProjectRelation is not found for the given project
					// and employee
					logger.error("EmployeeProjectRelation not found for Project ID: {} and Employee ID: {}", projectId,
							employeeId);
					// Return an empty list or handle the situation according to your business logic
					return Collections.emptyList();
				}
			} else {
				// Handle case where Project is not found
				logger.error("Project not found for ID: {}", projectId);
				// Return an empty list or handle the situation according to your business logic
				return Collections.emptyList();
			}

		} catch (Exception e) {
			logger.error("Error occurred while fetching timesheets", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> generateTimelogCSV(Integer id, Integer month, Integer year) throws CsvValidationException {
		logger.info("Generating timesheet CSV for project ID: {}, month: {}, year: {}", id, month, year);

		if (month < 1 || month > 12 || year < 0) {
			logger.error("Invalid month value: {}. Month must be between 1 and 12.", month);
			throw new CustomException("Invalid month value. Month must be between 1 and 12.", HttpStatus.BAD_REQUEST);
		}

		try {
			// Fetch project
			Optional<Project> projectOpt = projectRepo.findById(id);
			if (!projectOpt.isPresent()) {
				throw new CustomException("Project not found", HttpStatus.NOT_FOUND);
			}
			Project project = projectOpt.get();

			// Fetch timesheets for the given project
			List<Timelog> timesheets = timelogRepo.findByProjects(project);
			if (timesheets.isEmpty()) {
				throw new CustomException("No timesheets found for the given project", HttpStatus.NOT_FOUND);
			}

			// Write CSV
			String fileName = "nulltimesheet.csv";
			try (FileWriter writer = new FileWriter(fileName)) {
				writer.append("ID,Employee ID,Date,Hours\n");
				for (Timelog timesheet : timesheets) {
					String hours = (timesheet.getHours() == null) ? "" : timesheet.getHours().toString();
					writer.append(String.format("%d,%s,%s,%s\n", timesheet.getId(), timesheet.getEmpId(),
							timesheet.getDate(), hours));
				}
				writer.flush();
			} catch (IOException e) {
				logger.error("Error while writing CSV file", e);
				throw new CustomException("Failed to generate timesheet CSV", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>("CSV file written successfully: " + fileName, HttpStatus.OK);
		} catch (CustomException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error occurred", e);
			throw new CustomException("Failed to generate timesheet CSV", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> generateNullTimelogCSV(Integer id, Integer empId, LocalDate startDate, LocalDate endDate)
			throws CsvValidationException {

		Integer month = startDate.getMonthValue();
		Integer year = startDate.getYear();
		if (month < 1 || month > 12 || year < 0) {
			return ResponseEntity.badRequest().body("Invalid month value. Month must be between 1 and 12.");
		}
		List<Timelog> timesheets = getTimelogByProject(id);

		timelogRepo.findById(empId).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));

		List<Timelog> filteredTimesheets = timesheets.stream()
				.filter(timesheet -> !timesheet.getDate().isBefore(startDate))
				.filter(timesheet -> !timesheet.getDate().isAfter(endDate)).collect(Collectors.toList());

		if (filteredTimesheets.isEmpty()) {
			return ResponseEntity.badRequest()
					.body("No timesheet entries found for the specified month, year, and date range.");
		}

		Map<String, Map<LocalDate, String>> hoursMap = new HashMap<>();

		for (Timelog timesheet : filteredTimesheets) {
			String empIdStr = empId.toString();
			hoursMap.putIfAbsent(empIdStr, new HashMap<>());
			hoursMap.get(empIdStr).put(timesheet.getDate(),
					timesheet.getHours() != null ? timesheet.getHours().toString() : null);
		}

		StringBuilder csvData = new StringBuilder("Emp_id");

		for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
			boolean allNull = true;
			int day = currentDate.getDayOfMonth();
			int monthValue = currentDate.getMonthValue();
			int currentYear = currentDate.getYear();

			for (Map.Entry<String, Map<LocalDate, String>> entry : hoursMap.entrySet()) {
				String empIdStr = entry.getKey();
				Map<LocalDate, String> empHoursMap = entry.getValue();
				String hours = empHoursMap.get(currentDate);
				if (hours != null) {
					allNull = false;
					break;
				}
			}

			if (allNull) {
				csvData.append(",").append(String.format("%02d/%02d/%d", day, monthValue, currentYear));
			}
		}
		csvData.append("\n");

		for (Map.Entry<String, Map<LocalDate, String>> entry : hoursMap.entrySet()) {
			String empIdStr = entry.getKey();
			Map<LocalDate, String> empHoursMap = entry.getValue();
			boolean anyNonNull = empHoursMap.values().stream().anyMatch(hours -> hours != null);

			if (anyNonNull) {
				csvData.append(empIdStr);

				for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate
						.plusDays(1)) {
					String hours = empHoursMap.getOrDefault(currentDate, "00:00");
					// csvData.append(",").append(hours);
				}

				csvData.append("\n");
			}
		}

		String filename = "timelog.csv";
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(csvData.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error writing CSV file.");
		}

		return ResponseEntity.ok(csvData.toString() + "CSV file written successfully: " + filename);
	}
	
	public List<Timelog> getTimelogsByEmployeeIdAndDate(String empId, LocalDate date) {
        List<Timelog> timelogs = timelogRepo.findByEmpIdAndDate(empId, date);
        return timelogs.isEmpty() ? Collections.emptyList() : timelogs;
    }

}
