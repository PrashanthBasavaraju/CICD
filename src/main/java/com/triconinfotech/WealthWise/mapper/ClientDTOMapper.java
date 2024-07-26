package com.triconinfotech.WealthWise.mapper;

import com.triconinfotech.WealthWise.dto.ClientGETResponseDTO;
import com.triconinfotech.WealthWise.dto.ClientPOSTAndPUTRequestDTO;
import com.triconinfotech.WealthWise.dto.ClientPOSTAndPUTResponseDTO;
import com.triconinfotech.WealthWise.dto.ProjectGETResponseDTO;
import com.triconinfotech.WealthWise.entity.Client;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * ClientDTOMapper is a component responsible for mapping Client entities to
 * Client DTOs and vice versa.
 */
@Component
public class ClientDTOMapper {

    /** The api endpoint. */
    private static String API_ENDPOINT;

    /**
     * Private constructor to hide the implicit public one.
     */


    /**
     * Sets the api endpoint.
     *
     * @param apiEndpoint the new api endpoint
     */
    // Non-static method to initialize the static field
    @Value("${api.endpoint}")
    public void setApiEndpoint(String apiEndpoint) {
        API_ENDPOINT = apiEndpoint + "/api/clients/";
    }


    /**
     * Converts a Client entity to a ClientGETResponseDTO.
     *
     * @param client the Client entity
     * @return the corresponding ClientGETResponseDTO
     */
    public static ClientGETResponseDTO getClientDTO(Client client) {
        // Mapping client attributes to DTO
        List<ProjectGETResponseDTO> projectDTOs = client.getProjects() != null
                ? ProjectDTOMapper.getProjectDTOList(client.getProjects())
                : Collections.emptyList();
        return new ClientGETResponseDTO(client.getName(), client.getEmail(), client.getPhone(), client.getLocation(),
                API_ENDPOINT + client.getId(), projectDTOs);
    }

    /**
     * Converts a list of Client entities to a list of ClientGETResponseDTOs.
     *
     * @param clientList the list of Client entities
     * @return the corresponding list of ClientGETResponseDTOs
     */
    public static List<ClientGETResponseDTO> getClientDTOList(List<Client> clientList) {
        return clientList.stream().map(ClientDTOMapper::getClientDTO).collect(Collectors.toList());
    }

    /**
     * Converts a ClientPOSTAndPUTRequestDTO to a Client entity.
     *
     * @param postRequestDTO the ClientPOSTAndPUTRequestDTO
     * @param id             the ID of the client
     * @return the corresponding Client entity
     */
    public static Client getClient(ClientPOSTAndPUTRequestDTO postRequestDTO, Integer id) {
        Client client = new Client();
        client.setId(id);
        client.setName(postRequestDTO.name());
        client.setEmail(postRequestDTO.email());
        client.setPhone(postRequestDTO.phone());
        client.setLocation(postRequestDTO.location());
        return client;
    }

    /**
     * Converts a Client entity to a ClientPOSTAndPUTResponseDTO.
     *
     * @param client the Client entity
     * @return the corresponding ClientPOSTAndPUTResponseDTO
     */
    public static ClientPOSTAndPUTResponseDTO getClientPOSTAndPUTResponseDTO(Client client) {
        return new ClientPOSTAndPUTResponseDTO(client.getName(), client.getEmail(), client.getPhone(),
                client.getLocation(), API_ENDPOINT + client.getId());
    }
}
