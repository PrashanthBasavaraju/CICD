package com.triconinfotech.WealthWise.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.triconinfotech.WealthWise.entity.Client;

/**
 * The ClientRepository interface provides methods for accessing Client entities
 * in the database.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	/**
	 * Retrieves a list of clients with the specified offset and limit.
	 *
	 * @param offset the offset
	 * @param limit  the limit
	 * @return the list of clients
	 */
	@Query(value = "SELECT * FROM client WHERE deleted_at IS NULL ORDER BY client_id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Client> findAllWithOffsetAndLimit(@Param("offset") long offset, @Param("limit") long limit);

}
