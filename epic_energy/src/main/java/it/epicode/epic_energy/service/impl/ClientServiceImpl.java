package it.epicode.epic_energy.service.impl;

import it.epicode.epic_energy.contorllers.ClientController;
import it.epicode.epic_energy.dto.AddressDTO;
import it.epicode.epic_energy.dto.ClientCreateDTO;
import it.epicode.epic_energy.dto.ClientDTO;
import it.epicode.epic_energy.dto.ClientUpdateDTO;
import it.epicode.epic_energy.exceptions.ResourceNotFoundException;
import it.epicode.epic_energy.models.Address;
import it.epicode.epic_energy.models.Client;
import it.epicode.epic_energy.models.Municipality;
import it.epicode.epic_energy.models.Province;
import it.epicode.epic_energy.repositories.ClientRepository;
import it.epicode.epic_energy.repositories.MunicipalityRepository;
import it.epicode.epic_energy.repositories.ProvinceRepository;
import it.epicode.epic_energy.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ProvinceRepository provinceRepository;
    private final MunicipalityRepository municipalityRepository;
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);


    @Override
    public Page<ClientDTO> getAllClients(String companyName, Pageable pageable) {
        Page<Client> clients;
        if (companyName != null && !companyName.isEmpty()) {
            clients = clientRepository.findByCompanyNameContainingIgnoreCase(companyName, pageable);
        } else {
            clients = clientRepository.findAll(pageable);
        }
        return clients.map(client -> modelMapper.map(client, ClientDTO.class));
    }

    @Override
    public ClientDTO createClient(ClientCreateDTO clientCreateDTO) {
        // Map DTO to Entity
        logger.debug("Creating client: {}", clientCreateDTO.getCompanyName());
        Client client = modelMapper.map(clientCreateDTO, Client.class);

        // Handle Legal Address
        AddressDTO legalAddressDTO = clientCreateDTO.getLegalAddress();
        Address legalAddress = mapAddressDTOToEntity(legalAddressDTO);
        client.setLegalAddress(legalAddress);

        // Handle Operational Address if provided
        AddressDTO operationalAddressDTO = clientCreateDTO.getOperationalAddress();
        if (operationalAddressDTO != null) {
            Address operationalAddress = mapAddressDTOToEntity(operationalAddressDTO);
            client.setOperationalAddress(operationalAddress);
        }

        // Save Client
        Client savedClient = clientRepository.save(client);
        logger.debug("Client saved with ID: {}", savedClient.getId());

        return modelMapper.map(savedClient, ClientDTO.class);
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    @Transactional
    public ClientDTO updateClient(Long id, ClientUpdateDTO clientUpdateDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        // Update fields
        existingClient.setCompanyName(clientUpdateDTO.getCompanyName());
        existingClient.setVatNumber(clientUpdateDTO.getVatNumber());
        existingClient.setCompanyEmail(clientUpdateDTO.getCompanyEmail());
        existingClient.setPecEmail(clientUpdateDTO.getPecEmail());
        existingClient.setCompanyType(clientUpdateDTO.getCompanyType());
        existingClient.setContactEmail(clientUpdateDTO.getContactEmail());
        existingClient.setContactFirstName(clientUpdateDTO.getContactFirstName());
        existingClient.setContactLastName(clientUpdateDTO.getContactLastName());
        existingClient.setContactPhone(clientUpdateDTO.getContactPhone());
        existingClient.setContactMobile(clientUpdateDTO.getContactMobile());
        existingClient.setInsertionDate(clientUpdateDTO.getInsertionDate());
        existingClient.setLastContactDate(clientUpdateDTO.getLastContactDate());
        existingClient.setAnnualTurnover(clientUpdateDTO.getAnnualTurnover());
        existingClient.setCompanyLogo(clientUpdateDTO.getCompanyLogo());

        // Update Legal Address
        if (clientUpdateDTO.getLegalAddress() != null) {
            Address legalAddress = mapAddressDTOToEntity(clientUpdateDTO.getLegalAddress());
            existingClient.setLegalAddress(legalAddress);
        }

        // Update Operational Address
        if (clientUpdateDTO.getOperationalAddress() != null) {
            Address operationalAddress = mapAddressDTOToEntity(clientUpdateDTO.getOperationalAddress());
            existingClient.setOperationalAddress(operationalAddress);
        }

        // Save Updated Client
        Client updatedClient = clientRepository.save(existingClient);
        return modelMapper.map(updatedClient, ClientDTO.class);
    }

    @Override
    public void deleteClient(Long id) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        clientRepository.delete(existingClient);
    }

    private Address mapAddressDTOToEntity(AddressDTO addressDTO) {
        // Fetch Province
        Province province = provinceRepository.findById(addressDTO.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found with code: " + addressDTO.getProvinceCode()));

        // Fetch Municipality
        Municipality municipality = municipalityRepository.findByName(addressDTO.getLocality())
                .orElseThrow(() -> new ResourceNotFoundException("Municipality not found with name: " + addressDTO.getLocality()));

        // Ensure Municipality belongs to Province
        if (!municipality.getProvince().getCode().equals(province.getCode())) {
            throw new IllegalArgumentException("Municipality " + municipality.getName() + " does not belong to Province " + province.getName());
        }

        // Map Address
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setMunicipality(municipality);
        return address;
    }

}
