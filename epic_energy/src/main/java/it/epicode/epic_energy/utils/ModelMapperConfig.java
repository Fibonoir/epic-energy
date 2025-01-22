package it.epicode.epic_energy.utils;

import it.epicode.epic_energy.dto.InvoiceCreateDTO;
import it.epicode.epic_energy.models.Invoice;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.*;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Set strict matching to prevent unintended mappings
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Skip mapping clientId in InvoiceCreateDTO to Invoice
        mapper.typeMap(InvoiceCreateDTO.class, Invoice.class)
                .addMappings(m -> m.skip(Invoice::setClient));


        return mapper;
    }
}