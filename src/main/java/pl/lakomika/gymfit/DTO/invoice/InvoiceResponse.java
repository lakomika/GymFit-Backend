package pl.lakomika.gymfit.DTO.invoice;

import lombok.Data;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import pl.lakomika.gymfit.entity.invoice.Invoice;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class InvoiceResponse {
    private Long id;

    private Date startOfThePass;

    private Date endOfThePass;

    private Date dateCreate;

    private String typeOfTransaction;

    @OneToOne(cascade = {CascadeType.ALL})
    private GymMembership gymMembership;

    @OneToOne(cascade = {CascadeType.ALL})
    private CompanyDetails companyDetails;

    @OneToOne(cascade = {CascadeType.ALL})
    private ClientData clientData;

    public static InvoiceResponse toResponse(Invoice invoice) {
        val modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(invoice, InvoiceResponse.class);
    }
}

@Data
class ClientData {
    private String name;

    private String surname;

    private int phoneNumber;

    private String street;

    private String postcode;

    private String city;
}

@Data
class CompanyDetails {
    private String name;

    private String street;

    private String postcode;

    private String city;

    private String accountNumber;

    private String taxId;

}

@Data
class GymMembership {
    private String name;

    private BigDecimal priceTotalGross;

    private BigDecimal priceMonthGross;

    private BigDecimal priceMonthNet;

    private BigDecimal priceTotalNet;

    private Short taxRatePercent;

    private Short numberOfMonths;

    private String typeOfMembership;

}
