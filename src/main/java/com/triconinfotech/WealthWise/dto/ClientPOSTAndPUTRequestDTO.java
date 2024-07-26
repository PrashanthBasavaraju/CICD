package com.triconinfotech.WealthWise.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;



/**
 * A builder class for creating instances of ClientPOSTAndPUTRequestDTO.
 * This DTO (Data Transfer Object) represents the data required for creating or updating a client.
 */
@Builder
public record ClientPOSTAndPUTRequestDTO(
		@NotNull(message = "Name is mandatory")
        String name,
        @NotNull(message = "Email is mandatory")
        String email,
        @NotNull(message = "Phone is mandatory")
        String phone,
        @NotNull(message = "Location is mandatory")
        String location
        
) {
}
