package pl.lakomika.gymfit.DTO.client;

import lombok.Data;

import java.util.Date;

@Data
public class ClientProfileResponse {
    private String name;

    private String surname;

    private int phoneNumber;

    private String street;

    private String postcode;

    private String city;

    private Date dateOfBirth;

    private String email;
}
