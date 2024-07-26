package com.triconinfotech.WealthWise.dto;

import java.time.LocalDate;

import java.util.List;

/**
 * Represents the response data transfer object (DTO) for Project GET
 * operations. This DTO includes attributes such as name, description,
 * startDate, endDate, link, a list of employees (EPRGETResponseDTO), and a list
 * of timesheets (TimeLogGETResponseDTO).
 */
public record ProjectGETResponseDTO(Integer id, String name, String description, LocalDate startDate, LocalDate endDate,
		String link, List<EPRGETResponseDTO> employees, List<TimeLogGETResponseDTO> timesheets

) {
}
