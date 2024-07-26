package com.triconinfotech.WealthWise.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.triconinfotech.WealthWise.dto.TimeLogGETResponseDTO;
import com.triconinfotech.WealthWise.entity.Timelog;

/**
 * TimeLogDTOMapper is responsible for mapping Timelog entities to DTOs.
 */
@Component
public class TimeLogDTOMapper {

	/** The API endpoint for timesheets. */
	private static String API_ENDPOINT1;

	/**
	 * Sets the API endpoint for timesheets.
	 *
	 * @param apiEndpoint the new API endpoint for timesheets
	 */
	// Non-static method to initialize the static field
	@Value("${api.endpoint}")
	public void setApiEndpoint(String apiEndpoint) {
		API_ENDPOINT1 = apiEndpoint + "/api/timesheets/";
	};

	/**
	 * Converts a Timelog entity to a TimeLogGETResponseDTO.
	 *
	 * @param timesheet the Timelog entity
	 * @return the corresponding TimeLogGETResponseDTO
	 */
	public static TimeLogGETResponseDTO getTimesheetGetResponseDTO(Timelog timesheet) {
		return new TimeLogGETResponseDTO(timesheet.getStartTime(), timesheet.getHours(), timesheet.getDescription(),
				timesheet.getDate(),timesheet.getEmpId(), API_ENDPOINT1 + timesheet.getId()

		);
	}

	/**
	 * Converts a list of Timelog entities to a list of TimesheetGETResponseDTOs.
	 *
	 * @param timesheets the list of Timelog entities
	 * @return the corresponding list of TimesheetGETResponseDTOs
	 */
	public static List<TimeLogGETResponseDTO> getTimesheetGetResponseDTOList(List<Timelog> timesheet) {
		return timesheet.stream().map(TimeLogDTOMapper::getTimesheetGetResponseDTO).collect(Collectors.toList());
	}
}
