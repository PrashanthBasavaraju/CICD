package com.triconinfotech.WealthWise.service;

import java.util.List;

import com.triconinfotech.WealthWise.entity.Role;

/**
 * The Interface RoleServiceInterface.
 */
public interface RoleServiceInterface {
	
	/**
	 * Merge role.
	 *
	 * @param role the role
	 * @return the role
	 */
	Role mergeRole(Role role);
	
	/**
	 * Gets the all roles.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @return the all roles
	 */
	List<Role> getAllRoles(long offset, long limit);
	
	/**
	 * Gets the role by id.
	 *
	 * @param id the id
	 * @return the role by id
	 */
	Role getRoleById(Integer id);
	
	/**
	 * Delete role.
	 *
	 * @param id the id
	 */
	void deleteRole(Integer id);

}
