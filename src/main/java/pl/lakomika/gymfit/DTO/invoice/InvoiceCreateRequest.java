package pl.lakomika.gymfit.DTO.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InvoiceCreateRequest {
    private InvoiceCreateClientDataRequest client;

    private Date plannedStartOfThePass;

    private Long gymMembershipId;

}
