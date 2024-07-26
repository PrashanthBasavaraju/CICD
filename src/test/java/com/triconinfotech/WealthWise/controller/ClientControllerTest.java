package com.triconinfotech.WealthWise.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.triconinfotech.WealthWise.dto.ClientGETResponseDTO;
import com.triconinfotech.WealthWise.dto.ClientPOSTAndPUTRequestDTO;
import com.triconinfotech.WealthWise.dto.ClientPOSTAndPUTResponseDTO;
import com.triconinfotech.WealthWise.entity.Client;
import com.triconinfotech.WealthWise.service.ClientServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The Class ClientControllerTest.
 */
class ClientControllerTest {

	/** The client service. */
	@Mock
	ClientServiceInterface clientService;

	/** The client controller. */
	@InjectMocks
	ClientController clientController;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Gets the all clients should return list of clients.
	 *
	 * @return the all clients should return list of clients
	 */
	@Test
	void getAllClients_shouldReturnListOfClients() {

		when(clientService.getAllClients(anyLong(), anyLong())).thenReturn(Arrays.asList(createSampleClientEntity()));

		ResponseEntity<List<ClientGETResponseDTO>> responseEntity = clientController.getAllClients(0L, 25L);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(1, responseEntity.getBody().size());
	}

	/**
	 * Gets the client by id should return client if exists.
	 *
	 * @return the client by id should return client if exists
	 */
	@Test
	void getClientById_shouldReturnClientIfExists() {

		when(clientService.getClientById(anyInt())).thenReturn(createSampleClientEntity());

		ResponseEntity<ClientGETResponseDTO> responseEntity = clientController.getClientById(1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Adds the client should return created client.
	 */
	@Test
	void addClient_shouldReturnCreatedClient() {

		when(clientService.mergeClient(any())).thenReturn(createSampleClientEntity());

		ResponseEntity<ClientPOSTAndPUTResponseDTO> responseEntity = clientController
				.addClient(ClientPOSTAndPUTRequestDTO.builder().name("John Doe").email("john@example.com")
						.phone("1234567890").location("New York").build());

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Update client should return updated client.
	 */
	@Test
	void updateClient_shouldReturnUpdatedClient() {

		when(clientService.mergeClient(any())).thenReturn(createSampleClientEntity());

		ResponseEntity<ClientPOSTAndPUTResponseDTO> responseEntity = clientController
				.updateClient(ClientPOSTAndPUTRequestDTO.builder().name("John Doe").email("john@example.com")
						.phone("1234567890").location("New York").build(), 1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	/**
	 * Delete client should return http status ok.
	 */
	@Test
	void deleteClient_shouldReturnHttpStatusOk() {

		ResponseEntity<?> responseEntity = clientController.deleteClient(1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	/**
	 * Creates the sample client entity.
	 *
	 * @return the client
	 */
	private Client createSampleClientEntity() {
		Client client = new Client();
		client.setName("John Doe");
		client.setEmail("john@example.com");
		client.setPhone("1234567890");
		client.setLocation("New York");

		return client;
	}
}
