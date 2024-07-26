package com.triconinfotech.WealthWise.service;

import java.util.List;

import com.triconinfotech.WealthWise.entity.Client;

/**
 * The Interface ClientServiceInterface.
 */
public interface ClientServiceInterface {

	/**
	 * Merge client.
	 *
	 * @param client the client
	 * @return the client
	 */
	Client mergeClient(Client client);

	/**
	 * Gets the all clients.
	 *
	 * @param offset the offset
	 * @param limit  the limit
	 * @return the all clients
	 */
	List<Client> getAllClients(long offset, long limit);

	/**
	 * Gets the client by id.
	 *
	 * @param id the id
	 * @return the client by id
	 */
	Client getClientById(Integer id);

	/**
	 * Delete client.
	 *
	 * @param id the id
	 */
	void deleteClient(Integer id);

}
