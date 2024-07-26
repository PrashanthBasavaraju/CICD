package com.triconinfotech.WealthWise.dto;

/**
 * Represents the response data transfer object (DTO) for client POST and PUT operations.
 * This DTO includes attributes such as name, email, phone, location, and link.
 */
public record ClientPOSTAndPUTResponseDTO(
        String name,
        String email,
        String phone,
        String location,
        String link
) {
}
