package com.triconinfotech.WealthWise.controller;


import com.triconinfotech.WealthWise.dto.ClientGETResponseDTO;


import com.triconinfotech.WealthWise.dto.ClientPOSTAndPUTRequestDTO;
import com.triconinfotech.WealthWise.dto.ClientPOSTAndPUTResponseDTO;
import com.triconinfotech.WealthWise.mapper.ClientDTOMapper;
import com.triconinfotech.WealthWise.service.ClientServiceImpl;
import com.triconinfotech.WealthWise.service.ClientServiceInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The Class ClientController.
 * This controller provides endpoints for managing clients.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

	/** The client service. */
	private final ClientServiceImpl clientService;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    /**
     * Gets the all clients.
     *
     * @param offset the offset for pagination
     * @param limit  the limit for pagination
     * @return the all clients
     */
	@GetMapping
	public ResponseEntity<List<ClientGETResponseDTO>> getAllClients(
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "25") long limit) {

		if (offset < 0) {
			offset = 0;
		}
		logger.info("Fetching all clients.");
		List<ClientGETResponseDTO> clients = ClientDTOMapper
				.getClientDTOList(clientService.getAllClients(offset, limit));
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}

    /**
     * Gets the client by id.
     *
     * @param id the id of the client
     * @return the client by id
     */
	@GetMapping("/{id}")
	public ResponseEntity<ClientGETResponseDTO> getClientById(@PathVariable Integer id) {

		logger.info("Fetching client with ID: {}", id);
		ClientGETResponseDTO client = ClientDTOMapper.getClientDTO(clientService.getClientById(id));
		return new ResponseEntity<>(client, HttpStatus.OK);
	}

    /**
     * Adds the client.
     *
     * @param clientPOSTAndPUTRequestDTO the client POST and PUT request DTO
     * @return the response entity containing the created client
     */
	@PostMapping
	public ResponseEntity<ClientPOSTAndPUTResponseDTO> addClient(
			@Valid @RequestBody ClientPOSTAndPUTRequestDTO clientPOSTAndPUTRequestDTO) {
		logger.info("Adding a new client.");
		ClientPOSTAndPUTResponseDTO createdClientResponse = ClientDTOMapper.getClientPOSTAndPUTResponseDTO(
				clientService.mergeClient(ClientDTOMapper.getClient(clientPOSTAndPUTRequestDTO, 0)));
		return new ResponseEntity<>(createdClientResponse, HttpStatus.CREATED);

	}

    /**
     * Update client.
     *
     * @param clientPOSTAndPUTRequestDTO the client POST and PUT request DTO
     * @param id the id of the client to update
     * @return the response entity containing the updated client
     */
	@PutMapping("/{id}")
	public ResponseEntity<ClientPOSTAndPUTResponseDTO> updateClient(
			@RequestBody ClientPOSTAndPUTRequestDTO clientPOSTAndPUTRequestDTO, @PathVariable Integer id) {
		logger.info("Updating client with ID: {}", id);
		ClientPOSTAndPUTResponseDTO updatedClientResponse = ClientDTOMapper.getClientPOSTAndPUTResponseDTO(
				clientService.mergeClient(ClientDTOMapper.getClient(clientPOSTAndPUTRequestDTO, id)));
		return new ResponseEntity<>(updatedClientResponse, HttpStatus.OK);
	}

    /**
     * Delete client.
     *
     * @param id the id of the client to delete
     * @return the response entity
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
		logger.info("Deleting client with ID: {}", id);
		clientService.deleteClient(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
