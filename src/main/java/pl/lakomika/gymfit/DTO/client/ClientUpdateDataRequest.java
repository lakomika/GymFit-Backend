package pl.lakomika.gymfit.DTO.client;

import lombok.Data;

@Data
public class ClientUpdateDataRequest {
    private String email;

    private String name;

    private String surname;

    private int phoneNumber;

    private String street;

    private String postcode;

    private String city;

}
