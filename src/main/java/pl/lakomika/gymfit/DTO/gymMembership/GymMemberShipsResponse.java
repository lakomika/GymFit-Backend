package pl.lakomika.gymfit.DTO.gymMembership;

import lombok.Data;
import lombok.val;
import org.modelmapper.ModelMapper;
import pl.lakomika.gymfit.entity.GymMembership;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GymMemberShipsResponse {
    private Long id;

    private String name;

    private BigDecimal priceTotalGross;

    private BigDecimal priceMonthGross;

    private int NumberOfMonths;

    private String typeOfMembership;

    public static List<GymMemberShipsResponse> toResponse(List<GymMembership> gymMembershipList) {
        val modelMapper = new ModelMapper();
        return gymMembershipList.parallelStream().
                map(gymMembership -> modelMapper.map(gymMembership, GymMemberShipsResponse.class)).
                collect(Collectors.toList());
    }
}
