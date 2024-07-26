package com.triconinfotech.WealthWise.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Record TimeLogGETResponseDTO.
 *
 * @param start_time the start time
 * @param hours the hours
 * @param description the description
 * @param date the date
 * @param link the link
 */
public record TimeLogGETResponseDTO(
		LocalTime start_time,
		LocalTime hours,
		String description,
		LocalDate date,
		String empId,
		String link
		) {

}
