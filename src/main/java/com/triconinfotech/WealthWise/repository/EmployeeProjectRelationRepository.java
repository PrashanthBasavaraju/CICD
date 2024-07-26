package com.triconinfotech.WealthWise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.triconinfotech.WealthWise.entity.EmployeeProjectRelation;
import com.triconinfotech.WealthWise.entity.Project;

/**
 * The ClientRepository interface provides methods for accessing Client entities
 * in the database.
 */
@Repository
public interface EmployeeProjectRelationRepository extends JpaRepository<EmployeeProjectRelation, Integer> {

	/**
	 * Retrieves a list of clients with the specified offset and limit.
	 *
	 * @param offset the offset
	 * @param limit  the limit
	 * @return the list of clients
	 */
	@Query(value = "SELECT * FROM employee_project_relation WHERE deleted_at IS NULL ORDER BY epr_id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<EmployeeProjectRelation> findAllWithOffsetAndLimit(@Param("offset") long offset, @Param("limit") long limit);
	
	EmployeeProjectRelation findByProjectEPRAndEmployeeId(Project project, Integer empId);
	@Query(value = "SELECT * FROM employee_project_relation WHERE employee_id = :employeeId AND deleted_at IS NULL ORDER BY epr_id ASC", nativeQuery = true)
	List<EmployeeProjectRelation> findByEmployeeId(Integer employeeId);


}
