package pl.lakomika.gymfit.entity.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lakomika.gymfit.entity.CompanyDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Optional;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCompanyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String street;

    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "Error postcode")
    private String postcode;

    @NotNull
    private String city;

    @NotNull
    private String accountNumber;

    @NotNull
    private String taxId;

    public static InvoiceCompanyDetails fromCompanyDetails(Optional<CompanyDetails> companyDetails) {
        InvoiceCompanyDetails c = new InvoiceCompanyDetails();
        c.setName(companyDetails.get().getName());
        c.setAccountNumber(companyDetails.get().getAccountNumber());
        c.setCity(companyDetails.get().getCity());
        c.setPostcode(companyDetails.get().getPostcode());
        c.setStreet(companyDetails.get().getStreet());
        c.setTaxId(companyDetails.get().getTaxId());
        return c;

    }


}
