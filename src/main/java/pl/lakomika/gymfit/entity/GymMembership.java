package pl.lakomika.gymfit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
public class GymMembership {
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

    @NotNull
    private boolean active;
}

