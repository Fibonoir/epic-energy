package it.epicode.epic_energy.dto.external;

import lombok.Data;

@Data
public class ExternalMunicipalityDTO {
    private String codice;
    private String nome;
    private String nomeStraniero;
    private String codiceCatastale;
    private String cap;
    private String prefisso;
    private ExternalProvinceDTO provincia;
    private String email;
    private String pec;
    private String telefono;
    private String fax;
    private int popolazione;
    private CoordinateDTO coordinate;
}
