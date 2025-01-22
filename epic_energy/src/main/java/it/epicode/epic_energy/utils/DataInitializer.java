package it.epicode.epic_energy.utils;

import it.epicode.epic_energy.service.ExternalDataImportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ExternalDataImportService externalDataImportService;
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting data import from external API.");
        externalDataImportService.fetchAndImportMunicipalities();
        logger.info("Data import completed.");
    }
}
