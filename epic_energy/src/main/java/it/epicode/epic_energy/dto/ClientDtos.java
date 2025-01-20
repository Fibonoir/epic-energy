package it.epicode.epic_energy.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClientDtos {

    @Data
    public static class ClientRequest {
        private String companyName;
        private String vatNumber;
        // ... etc
    }

    @Data
    public static class ClientResponse {
        private Long id;
        private String companyName;
        private String vatNumber;
        // ... etc
        private BigDecimal annualTurnover;
        private LocalDate insertionDate;
        private LocalDate lastContactDate;
    }
}