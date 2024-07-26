package com.triconinfotech.WealthWise.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.triconinfotech.WealthWise.entity.Client;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.ClientRepository;
import com.triconinfotech.WealthWise.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

/**
 * The Class ClientServiceImplTest.
 */
class ClientServiceImplTest {

    /** The client repo. */
    @Mock
    private ClientRepository clientRepo;

    /** The logger. */
    @Mock
    private Logger logger;

    /** The client service. */
    @InjectMocks
    private ClientServiceImpl clientService;

    /**
     * Sets the up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        injectMockLogger(clientService, logger);
    }

    /**
     * Inject mock logger.
     *
     * @param service the service
     * @param mockLogger the mock logger
     */
    private void injectMockLogger(ClientServiceImpl service, Logger mockLogger) {
        try {
            java.lang.reflect.Field loggerField = ClientServiceImpl.class.getDeclaredField("logger");
            loggerField.setAccessible(true);
            loggerField.set(service, mockLogger);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Merge client should return saved client.
     */
    @Test
    void mergeClient_shouldReturnSavedClient() {
        Client client = createSampleClient();
        when(clientRepo.save(any(Client.class))).thenReturn(client);

        Client result = clientService.mergeClient(client);

        assertNotNull(result);
        assertEquals(client.getId(), result.getId());
        verify(logger).info("Merging client: {}", client);
    }

    /**
     * Merge client should throw custom exception when save fails.
     */
    @Test
    void mergeClient_shouldThrowCustomException_whenSaveFails() {
        Client client = createSampleClient();
        when(clientRepo.save(any(Client.class))).thenThrow(new RuntimeException("Database error"));

        CustomException exception = assertThrows(CustomException.class, () -> clientService.mergeClient(client));

        assertEquals("Failed to merge client", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        verify(logger).error("An error occurred while merging client: {}", "Database error");
    }

    /**
     * Gets the all clients should return list of clients.
     *
     * @return the all clients should return list of clients
     */
    @Test
    void getAllClients_shouldReturnListOfClients() {
        List<Client> clients = Arrays.asList(createSampleClient());
        when(clientRepo.findAllWithOffsetAndLimit(anyLong(), anyLong())).thenReturn(clients);

        List<Client> result = clientService.getAllClients(0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(logger).info("Fetching all clients with offset: {} and limit: {}", 0L, 10L);
    }

    /**
     * Gets the all clients should throw custom exception when fetch fails.
     *
     * @return the all clients should throw custom exception when fetch fails
     */
    @Test
    void getAllClients_shouldThrowCustomException_whenFetchFails() {
        when(clientRepo.findAllWithOffsetAndLimit(anyLong(), anyLong())).thenThrow(new RuntimeException("Database error"));

        CustomException exception = assertThrows(CustomException.class, () -> clientService.getAllClients(0, 10));

        assertEquals("Failed to fetch all clients", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        verify(logger).error("An error occurred while fetching all clients: {}", "Database error");
    }

    /**
     * Gets the client by id should return client if exists.
     *
     * @return the client by id should return client if exists
     */
    @Test
    void getClientById_shouldReturnClientIfExists() {
        Client client = createSampleClient();
        when(clientRepo.findById(anyInt())).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(1);

        assertNotNull(result);
        assertEquals(client.getId(), result.getId());
        verify(logger).info("Fetching client by id: {} from service implementation file",
        	    1);
    }

    /**
     * Gets the client by id should throw custom exception when client not found.
     *
     * @return the client by id should throw custom exception when client not found
     */
    @Test
    void getClientById_shouldThrowCustomException_whenClientNotFound() {
        when(clientRepo.findById(anyInt())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> clientService.getClientById(1));

        assertEquals("Client not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(logger).error("Client not found or already deleted with id: {}", 1);
    }

    /**
     * Delete client should delete client if exists.
     */
    @Test
    void deleteClient_shouldDeleteClientIfExists() {
        Client client = createSampleClient();
        when(clientRepo.findById(anyInt())).thenReturn(Optional.of(client));
        when(clientRepo.existsById(anyInt())).thenReturn(true);

        clientService.deleteClient(1);

        verify(clientRepo).deleteById(1);
        verify(logger).info("Deleting client with id: {}", 1);
        verify(logger).info("Client with id {} deleted successfully", 1);
    }

    /**
     * Delete client should throw custom exception when client not found.
     */
    @Test
    void deleteClient_shouldThrowCustomException_whenClientNotFound() {
        when(clientRepo.existsById(anyInt())).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> clientService.deleteClient(1));

        assertEquals("Client not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(logger).error("Client not found with id: {} from service implementation file",
        	    1);
    }

    /**
     * Delete client should throw custom exception when delete fails.
     */
    @Test
    void deleteClient_shouldThrowCustomException_whenDeleteFails() {
        when(clientRepo.existsById(anyInt())).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(clientRepo).deleteById(anyInt());

        CustomException exception = assertThrows(CustomException.class, () -> clientService.deleteClient(1));

        assertEquals("Client not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(logger).error("Client not found with id: {} from service implementation file", 1);
        
    }

    /**
     * Creates the sample client.
     *
     * @return the client
     */
    private Client createSampleClient() {
        Client client = new Client();
        client.setName("John Doe");
        client.setEmail("john@example.com");
        client.setPhone("1234567890");
        client.setLocation("New York");
 
        return client;
    }
}
