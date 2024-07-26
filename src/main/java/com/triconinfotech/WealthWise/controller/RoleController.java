package com.triconinfotech.WealthWise.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triconinfotech.WealthWise.entity.Role;
import com.triconinfotech.WealthWise.service.RoleServiceImpl;
import com.triconinfotech.WealthWise.service.RoleServiceInterface;

import lombok.AllArgsConstructor;

/**
 * The Class RoleController. This class is a REST controller for managing roles.
 * It provides endpoints to create, retrieve, and delete roles.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

	/** The role service. */

	private final RoleServiceImpl roleService;

	/**
	 * Creates a new role.
	 *
	 * @param role the role to be created
	 * @return the response entity containing the created role and HTTP status
	 */
	@PostMapping
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		Role createdRole = roleService.mergeRole(role);
		return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
	}

	/**
	 * Retrieves a role by its ID.
	 *
	 * @param id the ID of the role to retrieve
	 * 
	 * @return the response entity containing the retrieved role and HTTP status
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable Integer id) {
		Role role = roleService.getRoleById(id);
		if (role != null) {
			return new ResponseEntity<>(role, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves all roles.
	 *
	 * @return the response entity containing the list of roles and HTTP status
	 */
	@GetMapping
	public ResponseEntity<List<Role>> getAllRoles() {
		List<Role> roles = roleService.getAllRoles(0, 10); // Example: Offset 0, Limit 10
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	/**
	 * Deletes a role by its ID.
	 *
	 * @param id the ID of the role to delete
	 * @return the response entity with HTTP status indicating success or failure
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
		roleService.deleteRole(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
