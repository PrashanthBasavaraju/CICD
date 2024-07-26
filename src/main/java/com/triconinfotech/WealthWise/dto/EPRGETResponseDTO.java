package com.triconinfotech.WealthWise.dto;

import com.triconinfotech.WealthWise.entity.Role;

/**
 * Represents the response data transfer object (DTO) for Employee-Project-Role
 * (EPR) GET operations. This DTO includes attributes such as employeeId and
 * role.
 */
public record EPRGETResponseDTO(Integer id, Integer employeeId, Role role

) {
}