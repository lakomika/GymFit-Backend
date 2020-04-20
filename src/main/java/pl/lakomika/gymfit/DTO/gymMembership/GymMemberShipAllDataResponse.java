package pl.lakomika.gymfit.DTO.gymMembership;

import lombok.Data;
import org.modelmapper.ModelMapper;
import pl.lakomika.gymfit.entity.GymMembership;

import java.math.BigDecimal;

@Data
public class GymMemberShipAllDataResponse {
    private String name;

    private BigDecimal priceTotalGross;

    private BigDecimal priceMonthGross;

    private int NumberOfMonths;

    private String typeOfMembership;

    private Short taxRatePercent;

    public static GymMemberShipAllDataResponse toResponse(GymMembership gymMembership) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(gymMembership, GymMemberShipAllDataResponse.class);
    }
}
