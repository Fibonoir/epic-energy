package it.epicode.epic_energy.service;


import it.epicode.epic_energy.models.Municipality;
import it.epicode.epic_energy.models.Province;
import it.epicode.epic_energy.repositories.MunicipalityRepository;
import it.epicode.epic_energy.repositories.ProvinceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CsvImportService {

    private final ProvinceRepository provinceRepository;
    private final MunicipalityRepository municipalityRepository;

    @PostConstruct
    public void importCsvData() {
        importProvinces();
        importMunicipalities();
    }

    private void importProvinces() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/data/province-italiane.csv"), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Skip header if present
                if (line.startsWith("Sigla;Provincia;Regione")) {
                    continue;
                }

                // Format: Sigla;Provincia;Regione
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String sigla = parts[0].trim();
                    String nomeProvincia = parts[1].trim();
                    String regione = parts[2].trim();

                    // Assuming Codice Storico is derived or mapped externally
                    // If available, parse it; else, handle accordingly
                    String codiceStorico = mapSiglaToCodiceStorico(sigla);

                    // Check if province already exists to prevent duplicates
                    if (!provinceRepository.existsById(sigla)) {
                        Province province = new Province();
                        province.setSigla(sigla);
                        province.setNome(nomeProvincia);
                        province.setRegione(regione);
                        province.setCodiceStorico(codiceStorico);
                        provinceRepository.save(province);
                        System.out.println("Imported Province: " + sigla + " - " + nomeProvincia);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error importing provinces: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void importMunicipalities() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/data/comuni-italiani.csv"), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Skip header if present
                if (line.startsWith("Codice Provincia (Storico);Progressivo del Comune;Denominazione in italiano")) {
                    continue;
                }

                // Format: Codice Provincia (Storico);Progressivo del Comune;Denominazione in italiano;Provincia
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    String codiceProvinciaStorico = parts[0].trim();
                    // String progressivoComune = parts[1].trim(); // Not used in this context
                    String denominazione = parts[2].trim();
                    String nomeProvincia = parts[3].trim();

                    // Find the province by codiceStorico
                    Province province = provinceRepository.findByCodiceStorico(codiceProvinciaStorico)
                            .orElse(null);

                    if (province == null) {
                        // If not found by codiceStorico, try finding by nome
                        province = provinceRepository.findByNome(nomeProvincia).orElse(null);
                    }

                    if (province != null) {
                        // Check if municipality already exists to prevent duplicates
                        if (!municipalityRepository.existsByDenominazioneAndProvincia(denominazione, province)) {
                            Municipality municipality = new Municipality();
                            municipality.setDenominazione(denominazione);
                            municipality.setProvincia(province);
                            municipalityRepository.save(municipality);
                            System.out.println("Imported Municipality: " + denominazione + " in " + nomeProvincia);
                        }
                    } else {
                        System.err.println("Province not found for codiceStorico: " + codiceProvinciaStorico
                                + " or nomeProvincia: " + nomeProvincia);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error importing municipalities: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String mapSiglaToCodiceStorico(String sigla) {
        switch (sigla) {
            case "AG": return "001";
            case "AL": return "002";
            case "AN": return "003";
            case "AO": return "004";
            case "AQ": return "005";
            case "AR": return "006";
            case "AP": return "007";
            case "AT": return "008";
            case "AV": return "009";
            case "BA": return "010";
            case "BT": return "011";
            case "BL": return "012";
            case "BN": return "013";
            case "BG": return "014";
            case "BI": return "015";
            case "BO": return "016";
            case "BZ": return "017";
            case "BS": return "018";
            case "BR": return "019";
            case "CA": return "020";
            case "CL": return "021";
            case "CB": return "022";
            case "CI": return "023";
            case "CE": return "024";
            case "CT": return "025";
            case "CZ": return "026";
            case "CH": return "027";
            case "CO": return "028";
            case "CS": return "029";
            case "CR": return "030";
            case "KR": return "031";
            case "CN": return "032";
            case "EN": return "033";
            case "FM": return "034";
            case "FE": return "035";
            case "FI": return "036";
            case "FG": return "037";
            case "FC": return "038";
            case "FR": return "039";
            case "GE": return "040";
            case "GO": return "041";
            case "GR": return "042";
            case "IM": return "043";
            case "IS": return "044";
            case "SP": return "045";
            case "LT": return "046";
            case "LE": return "047";
            case "LC": return "048";
            case "LI": return "049";
            case "LO": return "050";
            case "LU": return "051";
            case "MC": return "052";
            case "MN": return "053";
            case "MS": return "054";
            case "MT": return "055";
            case "VS": return "056";
            case "ME": return "057";
            case "MI": return "058";
            case "MO": return "059";
            case "MB": return "060";
            case "NA": return "061";
            case "NO": return "062";
            case "NU": return "063";
            case "OG": return "064";
            case "OT": return "065";
            case "OR": return "066";
            case "PD": return "067";
            case "PA": return "068";
            case "PR": return "069";
            case "PV": return "070";
            case "PG": return "071";
            case "PU": return "072";
            case "PE": return "073";
            case "PC": return "074";
            case "PI": return "075";
            case "PT": return "076";
            case "PN": return "077";
            case "PZ": return "078";
            case "PO": return "079";
            case "RG": return "080";
            case "RA": return "081";
            case "RC": return "082";
            case "RE": return "083";
            case "RI": return "084";
            case "RN": return "085";
            case "Roma": return "086";
            case "RO": return "087";
            case "SA": return "088";
            case "SS": return "089";
            case "SV": return "090";
            case "SI": return "091";
            case "SR": return "092";
            case "SO": return "093";
            case "TA": return "094";
            case "TE": return "095";
            case "TR": return "096";
            case "TO": return "097";
            case "TP": return "098";
            case "TN": return "099";
            case "TV": return "100";
            case "TS": return "101";
            case "UD": return "102";
            case "VA": return "103";
            case "VE": return "104";
            case "VB": return "105";
            case "VC": return "106";
            case "VR": return "107";
            case "VV": return "108";
            case "VI": return "109";
            case "VT": return "110";
            default: return "000"; // Unknown or unlisted sigla
        }
    }

}