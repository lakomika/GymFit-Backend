package pl.lakomika.gymfit.DTO.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreateClientDataRequest {
    private String name;

    private String surname;

    @Column(unique = true, length = 9)
    private int phoneNumber;

    private String street;

    @Pattern(regexp = "([0-9]{2})-[0-9]{3}")
    private String postcode;

    private String city;
}
