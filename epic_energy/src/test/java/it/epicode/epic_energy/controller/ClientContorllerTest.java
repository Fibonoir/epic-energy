package it.epicode.epic_energy.controller;

import it.epicode.epic_energy.contorllers.ClientController;
import it.epicode.epic_energy.models.Client;
import it.epicode.epic_energy.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
        import java.util.List;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Test
    void testGetAllClients() throws Exception {
        Client client = new Client();
        client.setCompanyName("TestCompany");
        Page<Client> page = new PageImpl<>(List.of(client));

        Mockito.when(clientService.getAllClients(Mockito.any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].companyName").value("TestCompany"));
    }
}