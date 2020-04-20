package pl.lakomika.gymfit.DTO.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lakomika.gymfit.entity.invoice.Invoice;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InvoiceDataTransferResponse {
    private Long titleTransfer;

    private String nameCompany;

    private String streetCompany;

    private String postcodeCompany;

    private String cityCompany;

    private String accountNumber;

    public static InvoiceDataTransferResponse toResponse(Invoice newInvoice) {
        return InvoiceDataTransferResponse.builder()
                .titleTransfer(newInvoice.getId())
                .streetCompany(newInvoice.getInvoiceCompanyDetails().getStreet())
                .nameCompany(newInvoice.getInvoiceCompanyDetails().getName())
                .postcodeCompany(newInvoice.getInvoiceCompanyDetails().getPostcode())
                .cityCompany(newInvoice.getInvoiceCompanyDetails().getCity())
                .accountNumber(newInvoice.getInvoiceCompanyDetails().getAccountNumber())
                .build();

    }
}

