package pl.lakomika.gymfit.DTO.client;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class ClientCreateOnThePublicContentRequest {
    @Email
    private String email;

    private ClientRequest client;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&].{8,}")
    private String password;

    private String username;
}

@Data
class ClientRequest {
    private String name;

    private String surname;

    @Column(length = 9)
    private int phoneNumber;

    private String street;

    @Pattern(regexp = "([0-9]{2})-[0-9]{3}")
    private String postcode;

    private String city;

    private Date dateOfBirth;

}
