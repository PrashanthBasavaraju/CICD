package com.triconinfotech.WealthWise.dto;

import java.util.List;

/**
 * A DTO (Data Transfer Object) representing the response for a client GET request.
 * It encapsulates information about a client, including their name, email, phone number, location, link, and projects.
 */
public record ClientGETResponseDTO(
        String name,
        String email,
        String phone,
        String location,
        String link,
        List<ProjectGETResponseDTO> projects
) {
}
