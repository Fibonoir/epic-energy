package it.epicode.epic_energy.service;

import it.epicode.epic_energy.dto.ClientDtos;
import it.epicode.epic_energy.exceptions.ResourceNotFoundException;
import it.epicode.epic_energy.models.Client;
import it.epicode.epic_energy.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client createClient(ClientDtos clientDtos) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDtos, client);
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    public Client updateClient(Long id, Client clientDetails) {
        Client existing = getClientById(id);

        // Update fields
        existing.setCompanyName(clientDetails.getCompanyName());
        existing.setVatNumber(clientDetails.getVatNumber());
        // ... update the rest as needed

        return clientRepository.save(existing);
    }

    public void deleteClient(Long id) {
        Client client = getClientById(id);
        clientRepository.delete(client);
    }

    public Page<Client> filterClientsByName(String partialName, Pageable pageable) {
        return clientRepository.findByCompanyNameContainingIgnoreCase(partialName, pageable);
    }
}