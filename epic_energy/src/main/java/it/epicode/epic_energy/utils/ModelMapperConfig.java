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


        return new ModelMapper();
    }
}