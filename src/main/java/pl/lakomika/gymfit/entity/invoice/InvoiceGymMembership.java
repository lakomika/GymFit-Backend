package pl.lakomika.gymfit.entity.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import pl.lakomika.gymfit.entity.GymMembership;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceGymMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal priceTotalGross;

    @NotNull
    private BigDecimal priceMonthGross;

    @NotNull
    private Short taxRatePercent;

    @NotNull
    private BigDecimal priceMonthNet;

    @NotNull
    private BigDecimal priceTotalNet;

    @NotNull
    private Short numberOfMonths;

    @NotNull
    private String typeOfMembership;

    public static InvoiceGymMembership fromGymMembership(GymMembership gymMembership) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        InvoiceGymMembership invoiceGymMembership = modelMapper.map(gymMembership, InvoiceGymMembership.class);
        invoiceGymMembership.setId(null);
        return invoiceGymMembership;
    }
}
