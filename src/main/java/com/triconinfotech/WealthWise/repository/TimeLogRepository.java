//package com.triconinfotech.WealthWise.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.triconinfotech.WealthWise.entity.Project;
//import com.triconinfotech.WealthWise.entity.Timelog;
//
///**
// * The Interface TimeLogRepository.
// */
//@Repository
//public interface TimeLogRepository extends JpaRepository<Timelog, Integer> {
//	
//	/**
//	 * Find all with offset and limit.
//	 *
//	 * @param offset the offset
//	 * @param limit the limit
//	 * @return the list
//	 */
//	@Query(value = "SELECT * FROM timesheets   WHERE deleted_at IS NULL ORDER BY timesheet_id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
//	List<Timelog> findAllWithOffsetAndLimit(@Param("offset") long offset, @Param("limit") long limit);
//
//	List<Timelog> findByProjects(Project projectId);
//	List<Timelog> findByProjectsAndEmp_id(Project projects, String emp_id);
//
//}

package com.triconinfotech.WealthWise.repository;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.entity.Timelog;

/**
 * The Interface TimeLogRepository.
 */
@Repository
public interface TimeLogRepository extends JpaRepository<Timelog, Integer> {
	
	/**
	 * Find all with offset and limit.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 */
	@Query(value = "SELECT * FROM timelogs WHERE deleted_at IS NULL ORDER BY timelog_id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Timelog> findAllWithOffsetAndLimit(@Param("offset") long offset, @Param("limit") long limit);

	List<Timelog> findByProjects(Project project);
	@Query(value = "SELECT * FROM timelogs WHERE employee_id = :empId AND date = :date AND deleted_at IS NULL", nativeQuery = true)
	List<Timelog> findByEmpIdAndDate(String empId, LocalDate date);
	
	List<Timelog> findByEmpId(String empId);

}

