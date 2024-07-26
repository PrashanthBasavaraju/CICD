package com.triconinfotech.WealthWise.controller;

import com.triconinfotech.WealthWise.entity.Timelog;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.service.TimeLogServiceImpl;
import com.triconinfotech.WealthWise.service.TimeLogServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * The Class TimeLogController.
 * 
 * Controller for managing Timelog-related operations.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/timelogs")
@Tag(name = "Timelog")
@Data

/**
 * The Class TimelogControllerBuilder.
 */
@Builder
public class TimeLogController {

	/** The Timelog service. */
	private final TimeLogServiceImpl timelogService;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TimeLogController.class);

	/**
	 * Gets all Timelogs.
	 *
	 * @param offset the offset
	 * @param limit  the limit
	 * @return the all Timelogs
	 */
	@Operation(description = "Get All Timelog", summary = "This endpoint is to get the List of Timelogs  ")
	@GetMapping
	public ResponseEntity<List<Timelog>> getAllTimelogs(
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "25") long limit) {
		if (offset < 0) {
			offset = 0;
		}
		logger.info("Fetching all Timelogs.");
		List<Timelog> Timelogs = timelogService.getAllTimelogs(offset, limit);
		return new ResponseEntity<>(Timelogs, HttpStatus.OK);
	}

	/**
	 * Gets Timelog by id.
	 *
	 * @param id the id
	 * @return the Timelog by id
	 */
	@Operation(description = "Get Individual Timelog", summary = "This endpoint is to get the Timelog based on Id  ")
	@GetMapping("/{id}")
	public ResponseEntity<Timelog> getTimelogById(@PathVariable Integer id) {

		logger.info("Fetching Timelog with ID: {}", id);
		Timelog Timelog = timelogService.getTimelogById(id);
		System.out.println(Timelog.getDate());
		return new ResponseEntity<>(Timelog, HttpStatus.OK);

	}

	/**
	 * Adds a Timelog.
	 *
	 * @param Timelog the Timelog
	 * @return the response entity
	 */
	@Operation(description = "Add Timelog", summary = "This endpoint is to add the Timelog")
	@PostMapping
	public ResponseEntity<Timelog> addTimelog(@Valid @RequestBody Timelog Timelog) {

		logger.info("Adding a new Timelog.");

		Timelog createdTimelog = timelogService.mergeTimelog(Timelog);

		return new ResponseEntity<>(createdTimelog, HttpStatus.CREATED);

	}

	/**
	 * Updates Timelog.
	 *
	 * @param Timelog the Timelog
	 * @param id      the id
	 * @return the response entity
	 */
	@Operation(description = "Update Individual Timelog", summary = "This endpoint is to update the Timelog based on Id ")
	@PutMapping("/{id}")
	public ResponseEntity<Timelog> updateTimelog(@RequestBody Timelog Timelog, @PathVariable Integer id) {
		logger.info("Updating Timelog with ID: {}", id);
		Timelog.setId(id);
		Timelog updatedTimelog = timelogService.mergeTimelog(Timelog);
		return new ResponseEntity<>(updatedTimelog, HttpStatus.OK);

	}

	/**
	 * Deletes a Timelog.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Operation(description = "Delete Individual Timelog", summary = "This endpoint is to delete the Timelog based on Id  ")
	@DeleteMapping("/{id}")
	public ResponseEntity<Timelog> deleteTimelog(@PathVariable Integer id) {
		logger.info("Deleting Timelog with ID: {}", id);
		timelogService.deleteTimelog(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/generateNullTimelogCSV")
	public ResponseEntity<?> generateNullTimelogCSV(@RequestParam("projectId") Integer projectId,
			@RequestParam("empId") Integer empId,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		try {
			return timelogService.generateNullTimelogCSV(projectId, empId, startDate, endDate);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/null")
	public ResponseEntity<List<Timelog>> getTimelogsByEmployeeIdAndDate(@RequestParam String empId,
			@RequestParam String date) {

		LocalDate localDate = LocalDate.parse(date); // Parse date string to LocalDate

		List<Timelog> timelogs = timelogService.getTimelogsByEmployeeIdAndDate(empId, localDate);

		return ResponseEntity.ok(timelogs);
	}

}
