package com.triconinfotech.WealthWise.service;

import java.util.List;

import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;

/**
 * The Interface EmployeeProjectRelationInterface.
 */
public interface EmployeeProjectRelationInterface {

	/**
	 * Merge EPR.
	 *
	 * @param EPR the epr
	 * @return the employee project relation
	 */
	EmployeeProjectRelation mergeEPR(EmployeeProjectRelation EPR);

	/**
	 * Gets the all EPR relations.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @return the all EPR relations
	 */
	List<EmployeeProjectRelation> getAllEPRRelations(long offset, long limit);

	/**
	 * Gets the EPR by id.
	 *
	 * @param id the id
	 * @return the EPR by id
	 */
	EmployeeProjectRelation getEPRById(Integer id);

	/**
	 * Delete EPR.
	 *
	 * @param id the id
	 */
	void deleteEPR(Integer id);
	

}
