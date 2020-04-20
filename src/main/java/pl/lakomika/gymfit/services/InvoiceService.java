package pl.lakomika.gymfit.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pl.lakomika.gymfit.DTO.invoice.InvoiceCreateRequest;
import pl.lakomika.gymfit.DTO.invoice.InvoiceHistoryOfOrdersPassResponse;
import pl.lakomika.gymfit.DTO.invoice.InvoicesPendingResponse;

public interface InvoiceService {
    ResponseEntity<?> buyGymPassByClient(InvoiceCreateRequest invoiceCreate);

    Page<InvoiceHistoryOfOrdersPassResponse> getHistoryOfOrdersPass(int page, int size);

    ResponseEntity<?> cancellationOrder(Long cancellationOrderRequest);

    Page<InvoicesPendingResponse> getPendingInvoices(int page, int size);

    ResponseEntity<?> confirmDeliveryOfFunds(Long idInvoice);

    ResponseEntity<?> isPending();

    ResponseEntity<?> saveInvoiceByReceptionistOrAdmin(Long gymMembershipId, Long numberCard);

    ResponseEntity<?> getPaidInvoiceById(Long invoiceId);

    ResponseEntity<?> getDataTransferByUnpaidInvoiceId(Long invoiceId);
}
