package pl.lakomika.gymfit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetails {
    @Id
    private Long id;

    private String name;

    private String street;

    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "Error post code")
    private String postcode;

    private String city;

    private String accountNumber;

    private String taxId;

    public CompanyDetails(Long id) {
        this.id = id;
    }
}
