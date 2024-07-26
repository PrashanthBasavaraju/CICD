package com.triconinfotech.WealthWise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.triconinfotech.WealthWise.entity.Project;
import com.triconinfotech.WealthWise.entity.Role;

/**
 * The Interface RoleRepository.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
	@Query(value = "SELECT * FROM roles WHERE deleted_at IS NULL ORDER BY id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Role> findAllWithOffsetAndLimit(@Param("offset") long offset, @Param("limit") long limit);
}
