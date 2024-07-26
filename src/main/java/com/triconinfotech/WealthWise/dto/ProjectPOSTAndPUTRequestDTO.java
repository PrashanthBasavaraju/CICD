package com.triconinfotech.WealthWise.dto;

import java.time.LocalDate;

/**
 * The Record ProjectPOSTAndPUTRequestDTO.
 *
 * @param name the name
 * @param description the description
 * @param start_date the start date
 * @param end_date the end date
 */
public record ProjectPOSTAndPUTRequestDTO(

		String name, String description, LocalDate start_date, LocalDate end_date

) {

}
