package it.epicode.epic_energy.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDTO {

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @NotBlank
    private String locality;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String municipalityName;

    @NotBlank
    private String provinceCode;
}
