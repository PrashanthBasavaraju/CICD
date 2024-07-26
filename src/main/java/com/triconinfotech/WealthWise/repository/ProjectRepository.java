package com.triconinfotech.WealthWise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.triconinfotech.WealthWise.entity.Project;

/**
 * The ProjectRepository interface provides methods for accessing Project entities in the database.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer>{
	
	/**
	 * Retrieves a list of projects with the specified offset and limit.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list of projects
	 */
	@Query(value = "SELECT * FROM project WHERE deleted_at IS NULL ORDER BY project_id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Project> findAllWithOffsetAndLimit(@Param("offset") long offset, @Param("limit") long limit);
	
	List<Project> findByName(String name);
	
	List<Project> findByClientId(Integer clientId);
}
