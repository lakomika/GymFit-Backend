package pl.lakomika.gymfit.DTO.gymMembership;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GymMembershipAddRequest {
    private String name;

    private BigDecimal priceMonthGross;

    private Short NumberOfMonths;

    private String typeOfMembership;

}
