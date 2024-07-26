package com.triconinfotech.WealthWise.service;

import java.util.List;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triconinfotech.WealthWise.entity.Client;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.ClientRepository;

import lombok.AllArgsConstructor;

/**
 * The Class ClientServiceImpl.
 */
@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientServiceInterface {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	/** The client repo. */
	private final ClientRepository clientRepo;

	private static final String CLIENT_NOT_FOUND = "Client not found";

	/**
	 * Merge client.
	 *
	 * @param client the client
	 * @return the client
	 */
	@Override
	@Transactional
	public Client mergeClient(Client client) {
		try {
			logger.info("Merging client: {}", client);
			return clientRepo.save(client);
		} catch (Exception e) {
			logger.error("An error occurred: {}", e);
			logger.error("An error occurred while merging client: {}", e.getMessage());
			throw new CustomException("Failed to merge client", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Gets the all clients.
	 *
	 * @param offset the offset
	 * @param limit  the limit
	 * @return the all clients
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Client> getAllClients(long offset, long limit) {
		try {
			logger.info("Fetching all clients with offset: {} and limit: {}", offset, limit);
			long adjustedOffset = offset > 0 ? offset - 1 : 0;
			return clientRepo.findAllWithOffsetAndLimit(adjustedOffset, limit);
		} catch (Exception e) {
			logger.error("An error occurred while fetching all clients: {}", e.getMessage());
			throw new CustomException("Failed to fetch all clients", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Gets the client by id.
	 *
	 * @param id the id
	 * @return the client by id
	 * @throws CustomException the custom exception
	 */
	@Override
	@Transactional(readOnly = true)
	public Client getClientById(Integer id) throws CustomException {
		logger.info("Fetching client by id: {} from service implementation file", id);
		Optional<Client> clientOptional = clientRepo.findById(id);
		return clientOptional.filter(client -> client.getDeletedAt() == null).orElseThrow(() -> {
			logger.error("Client not found or already deleted with id: {}", id);
			return new CustomException(CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
		});
	}

	/**
	 * Delete client.
	 *
	 * @param id the id
	 */

	@Override
	@Transactional
	public void deleteClient(Integer id) {
		logger.info("Deleting client with id: {}", id);

		Optional<Client> clientOptional = clientRepo.findById(id);
		if (clientOptional.isPresent()) {
			Client client = clientOptional.get();
			if (client.getDeletedAt() == null) {
				// Proceed with deletion
				clientRepo.deleteById(id);
				logger.info("Client with id {} deleted successfully", id);
			} else {
				logger.error("Client with id {} is already deleted", id);
				throw new CustomException(CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.error("Client not found with id: {} from service implementation file", id);
			throw new CustomException(CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}
}
