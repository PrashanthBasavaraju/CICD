package com.triconinfotech.WealthWise.mapper;

import com.triconinfotech.WealthWise.dto.EPRGETResponseDTO;
import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EPRDTOMapper is responsible for mapping EmployeeProjectRelation entities to
 * EPRGETResponseDTOs.
 */
public class EPRDTOMapper {

	/**
	 * Converts an EmployeeProjectRelation entity to an EPRGETResponseDTO.
	 *
	 * @param employeeProjectRelation the EmployeeProjectRelation entity
	 * @return the corresponding EPRGETResponseDTO
	 */
	public static EPRGETResponseDTO getEPRGetResponseDTO(EmployeeProjectRelation employeeProjectRelation) {
		return new EPRGETResponseDTO(employeeProjectRelation.getId(),employeeProjectRelation.getEmployeeId(), employeeProjectRelation.getRoletype());
	}

	/**
	 * Converts a list of EmployeeProjectRelation entities to a list of
	 * EPRGETResponseDTOs.
	 *
	 * @param employeeProjectRelationList the list of EmployeeProjectRelation
	 *                                    entities
	 * @return the corresponding list of EPRGETResponseDTOs
	 */
	public static List<EPRGETResponseDTO> getEPRGetResponseDTOList(
			List<EmployeeProjectRelation> employeeProjectRelationList) {
		return employeeProjectRelationList.stream().map(EPRDTOMapper::getEPRGetResponseDTO)
				.collect(Collectors.toList());
	}
}