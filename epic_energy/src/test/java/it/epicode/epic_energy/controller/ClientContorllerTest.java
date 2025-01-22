package it.epicode.epic_energy.controller;

import it.epicode.epic_energy.models.Client;
import it.epicode.epic_energy.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testCreateClient() {
        // Prepare DTO
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setCompanyName("Integration Test Co");
        clientDTO.setVatNumber("98765432109");
        clientDTO.setCompanyEmail("info@integrationtest.com");
        clientDTO.setPecEmail("pec@integrationtest.com");
        clientDTO.setCompanyType("SPA");
        clientDTO.setContactPhone("0112233445");
        clientDTO.setContactEmail("jane.doe@integrationtest.com");
        clientDTO.setContactFirstName("Jane");
        clientDTO.setContactLastName("Doe");
        clientDTO.setContactMobile("0556677889");
        clientDTO.setInsertionDate(LocalDate.now());
        clientDTO.setLastContactDate(LocalDate.now().minusDays(1));
        clientDTO.setAnnualTurnover(new BigDecimal("750000.00"));
        clientDTO.setCompanyLogo(null);

        ClientDtos.AddressDTO legalAddress = new ClientDtos.AddressDTO();
        legalAddress.setStreet("Via Libertà");
        legalAddress.setHouseNumber("50");
        legalAddress.setLocality("Turin");
        legalAddress.setPostalCode("10100");

        ClientDtos.MunicipalityDTO municipality = new ClientDtos.MunicipalityDTO();
        municipality.setName("Turin");
        ClientDtos.ProvinceDTO province = new ClientDtos.ProvinceDTO();
        province.setCode("TO");
        province.setName("Torino");
        municipality.setProvince(province);
        legalAddress.setMunicipality(municipality);
        clientDTO.setLegalAddress(legalAddress);

        ClientDtos.AddressDTO operationalAddress = new ClientDtos.AddressDTO();
        operationalAddress.setStreet("Via Milano");
        operationalAddress.setHouseNumber("25");
        operationalAddress.setLocality("Milan");
        operationalAddress.setPostalCode("20100");

        ClientDtos.MunicipalityDTO opMunicipality = new ClientDtos.MunicipalityDTO();
        opMunicipality.setName("Milan");
        ClientDtos.ProvinceDTO opProvince = new ClientDtos.ProvinceDTO();
        opProvince.setCode("MI");
        opProvince.setName("Milano");
        opMunicipality.setProvince(opProvince);
        operationalAddress.setMunicipality(opMunicipality);
        clientDTO.setOperationalAddress(operationalAddress);

        // Set headers with Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("your_jwt_token_here"); // Replace with a valid token

        HttpEntity<ClientDtos.ClientDTO> request = new HttpEntity<>(clientDTO, headers);

        // Send POST request
        ResponseEntity<Client> response = restTemplate.postForEntity("/api/clients", request, Client.class);

        // Assert response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Client savedClient = response.getBody();
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getCompanyName()).isEqualTo("Integration Test Co");

        // Verify in repository
        Client clientFromDb = clientRepository.findById(savedClient.getId()).orElse(null);
        assertThat(clientFromDb).isNotNull();
        assertThat(clientFromDb.getCompanyName()).isEqualTo("Integration Test Co");
    }
}
