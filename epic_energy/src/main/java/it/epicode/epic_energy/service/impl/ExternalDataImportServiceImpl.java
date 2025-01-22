package it.epicode.epic_energy.service.impl;

import it.epicode.epic_energy.dto.external.ExternalMunicipalityDTO;
import it.epicode.epic_energy.models.Municipality;
import it.epicode.epic_energy.models.Province;
import it.epicode.epic_energy.repositories.MunicipalityRepository;
import it.epicode.epic_energy.repositories.ProvinceRepository;
import it.epicode.epic_energy.service.ExternalDataImportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalDataImportServiceImpl implements ExternalDataImportService {

    private final RestTemplate restTemplate;
    private final ProvinceRepository provinceRepository;
    private final MunicipalityRepository municipalityRepository;
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(ExternalDataImportServiceImpl.class);

    private static final String EXTERNAL_API_URL = "https://axqvoqvbfjpaamphztgd.functions.supabase.co/comuni";

    @Override
    @Transactional
    public void fetchAndImportMunicipalities() {
        logger.info("Fetching municipalities data from external API.");

        ExternalMunicipalityDTO[] response = restTemplate.getForObject(EXTERNAL_API_URL, ExternalMunicipalityDTO[].class);

        if (response == null || response.length == 0) {
            logger.warn("No data received from external API.");
            return;
        }

        List<ExternalMunicipalityDTO> municipalities = Arrays.asList(response);
        logger.info("Received {} municipalities from external API.", municipalities.size());

        for (ExternalMunicipalityDTO dto : municipalities) {
            try {
                // Extract necessary fields
                String provinceCode = dto.getProvincia().getSigla();
                String provinceName = dto.getProvincia().getNome();
                String region = dto.getProvincia().getRegione();

                // Check if province exists
                Province province = provinceRepository.findById(provinceCode).orElse(null);
                if (province == null) {
                    // Create and save new province
                    province = new Province();
                    province.setCode(provinceCode);
                    province.setName(provinceName);
                    province.setRegion(region);
                    provinceRepository.save(province);
                    logger.info("Saved new province: {}", provinceName);
                }

                // Check if municipality already exists
                boolean exists = municipalityRepository.findByNameAndProvince_Code(dto.getNome(), provinceCode).isPresent();
                if (!exists) {
                    // Create and save new municipality
                    Municipality municipality = new Municipality();
                    municipality.setName(dto.getNome());
                    municipality.setCap(dto.getCap());
                    municipality.setProvince(province);
                    municipalityRepository.save(municipality);
                    logger.info("Saved new municipality: {}", dto.getNome());
                } else {
                    logger.debug("Municipality already exists: {}", dto.getNome());
                }

            } catch (Exception e) {
                logger.error("Error importing municipality {}: {}", dto.getNome(), e.getMessage());
            }
        }

        logger.info("Completed importing municipalities.");
    }
}