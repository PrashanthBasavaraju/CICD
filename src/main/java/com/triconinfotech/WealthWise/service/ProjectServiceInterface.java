package com.triconinfotech.WealthWise.service;

import java.util.List;

import com.triconinfotech.WealthWise.entity.Project;

/**
 * The Interface ProjectServiceInterface.
 */
public interface ProjectServiceInterface {
	
	/**
	 * Merge project.
	 *
	 * @param project the project
	 * @return the project
	 */
	Project mergeProject(Project project);
	
	/**
	 * Gets the all project.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @return the all project
	 */
	List<Project> getAllProject(long offset, long limit);
	
	/**
	 * Gets the project by id.
	 *
	 * @param pid the pid
	 * @return the project by id
	 */
	Project getProjectById(Integer pid);
	
	
	/**
	 * Delete project.
	 *
	 * @param pid the pid
	 */
	void deleteProject(Integer pid);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Project> findByName(String name);
	
}
