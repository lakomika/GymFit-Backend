package pl.lakomika.gymfit.DTO.invoice;

import lombok.Data;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import pl.lakomika.gymfit.entity.invoice.Invoice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class InvoicesPendingResponse {
    private Long id;

    private String name;

    private String surname;

    private Date plannedStartOfThePass;

    public static List<InvoicesPendingResponse> toResponse(List<Invoice> pendingInvoices) {
        val modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return pendingInvoices.parallelStream().
                map(invoice -> modelMapper.map(invoice, InvoicesPendingResponse.class)).
                collect(Collectors.toList());
    }
}
