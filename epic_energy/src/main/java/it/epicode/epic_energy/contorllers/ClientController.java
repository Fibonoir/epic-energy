package it.epicode.epic_energy.contorllers;

import it.epicode.epic_energy.dto.*;
import it.epicode.epic_energy.models.Client;
import it.epicode.epic_energy.repositories.ClientRepository;
import it.epicode.epic_energy.service.impl.ClientServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientServiceImpl clientService;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderByCompanyName") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClientDTO> clientDTOs;

        clientDTOs = clientService.getClients(sortBy, pageable);
        return ResponseEntity.ok(clientDTOs);
}


    @GetMapping("/search")
    public ResponseEntity<Page<ClientDTO>> searchClients(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) BigDecimal minAnnualTurnover,
            @RequestParam(required = false) BigDecimal maxAnnualTurnover,
            @RequestParam(required = false) String contactFirstName,
            @RequestParam(required = false) String contactLastName,
            @RequestParam(required = false) Date startInsertionDate,
            @RequestParam(required = false) Date endInsertionDate,
            @RequestParam(required = false) Date startLastContactDate,
            @RequestParam(required = false) Date endLastContactDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClientDTO> clientDTOs = clientService.filterClients(
                companyName, minAnnualTurnover, maxAnnualTurnover,
                contactFirstName, contactLastName,
                startInsertionDate, endInsertionDate,
                startLastContactDate, endLastContactDate, pageable);

        return ResponseEntity.ok(clientDTOs);
    }


    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        logger.debug("Received request to create client: {}", clientCreateDTO.getCompanyName());
        ClientDTO createdClient = clientService.createClient(clientCreateDTO);
        logger.debug("Client created with ID: {}", createdClient.getId());
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        logger.debug("Fetching client with ID: {}", id);
        ClientDTO client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {
        logger.debug("Updating client with ID: {}", id);
        ClientDTO updatedClient = clientService.updateClient(id, clientUpdateDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        logger.debug("Deleting client with ID: {}", id);
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}