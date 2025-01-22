package it.epicode.epic_energy.service;


import it.epicode.epic_energy.dto.ClientCreateDTO;
import it.epicode.epic_energy.dto.ClientDTO;
import it.epicode.epic_energy.dto.ClientUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Page<ClientDTO> getAllClients(String companyName, Pageable pageable);
    ClientDTO createClient(ClientCreateDTO clientCreateDTO);
    ClientDTO getClientById(Long id);
    ClientDTO updateClient(Long id, ClientUpdateDTO clientUpdateDTO);
    void deleteClient(Long id);
}