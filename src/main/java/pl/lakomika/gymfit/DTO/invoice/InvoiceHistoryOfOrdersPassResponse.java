package pl.lakomika.gymfit.DTO.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import pl.lakomika.gymfit.entity.invoice.Invoice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceHistoryOfOrdersPassResponse {
    private Long id;

    private String name;

    private String surname;

    private int phoneNumber;

    private String street;

    private String postcode;

    private String city;

    private Date plannedStartOfThePass;

    private Date startOfThePass;

    private Date endOfThePass;

    private String status;

    private String typeOfTransaction;


    public static List<InvoiceHistoryOfOrdersPassResponse> toResponse(List<Invoice> historyOfOrders) {
        return historyOfOrders.parallelStream().map(invoice -> {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper.map(invoice, InvoiceHistoryOfOrdersPassResponse.class);
        }).collect(Collectors.toList());

    }
}
