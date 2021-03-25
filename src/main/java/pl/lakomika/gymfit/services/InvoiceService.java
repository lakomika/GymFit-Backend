package pl.lakomika.gymfit.services;

import org.springframework.data.domain.Page;
import pl.lakomika.gymfit.DTO.invoice.*;

public interface InvoiceService {
    InvoiceDataTransferResponse buyGymPassByClient(InvoiceCreateRequest invoiceCreate);

    Page<InvoiceHistoryOfOrdersPassResponse> getHistoryOfOrdersPass(int page, int size);

    void cancellationOrder(Long cancellationOrderRequest);

    Page<InvoicesPendingResponse> getPendingInvoices(int page, int size);

    void confirmDeliveryOfFunds(Long idInvoice);

    InvoiceCreateIsPendingInvoiceResponse isPending();

    void saveInvoiceByReceptionistOrAdmin(Long gymMembershipId, Long numberCard);

    InvoiceResponse getPaidInvoiceById(Long invoiceId);

    InvoiceDataTransferResponse getDataTransferByUnpaidInvoiceId(Long invoiceId);
}
