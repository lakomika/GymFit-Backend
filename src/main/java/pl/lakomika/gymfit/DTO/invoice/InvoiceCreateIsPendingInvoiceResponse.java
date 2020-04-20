package pl.lakomika.gymfit.DTO.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceCreateIsPendingInvoiceResponse {
    private boolean isPendingInvoice;
}
