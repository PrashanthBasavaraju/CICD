package com.triconinfotech.WealthWise.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.opencsv.exceptions.CsvValidationException;
import com.triconinfotech.WealthWise.entity.Timelog;

/**
 * The Interface TimeLogServiceInterface.
 */
public interface TimeLogServiceInterface {

	/**
	 * Merge timesheet.
	 *
	 * @param timesheet the timesheet
	 * @return the timesheet
	 */
	Timelog mergeTimelog(Timelog timelog);

	List<Timelog> getAllTimelogs(long offset, long limit);

	Timelog getTimelogById(Integer id);

	void deleteTimelog(Integer id);

	List<Timelog> getTimelogByProject(Integer projectId);

	ResponseEntity<?> generateTimelogCSV(Integer id, Integer month, Integer year) throws CsvValidationException;

	public ResponseEntity<?> generateNullTimelogCSV(Integer id, Integer empId, LocalDate startDate, LocalDate endDate)
			throws CsvValidationException;

}
