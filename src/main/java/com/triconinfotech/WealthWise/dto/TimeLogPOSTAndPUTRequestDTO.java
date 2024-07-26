package com.triconinfotech.WealthWise.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Record TimeLogPOSTAndPUTRequestDTO.
 *
 * @param start_time the start time
 * @param hours the hours
 * @param description the description
 * @param date the date
 */
public record TimeLogPOSTAndPUTRequestDTO(
		LocalTime start_time,
		LocalTime hours,
		String description,
		LocalDate date
		) {

}
