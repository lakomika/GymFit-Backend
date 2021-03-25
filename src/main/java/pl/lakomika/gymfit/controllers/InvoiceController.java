package pl.lakomika.gymfit.controllers;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lakomika.gymfit.DTO.invoice.*;
import pl.lakomika.gymfit.services.InvoiceService;

@CrossOrigin
@RestController
@RequestMapping("/api/invoice/")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/buy-gym-pass-by-client")
    public InvoiceDataTransferResponse buyGymPassByClient(@RequestBody InvoiceCreateRequest invoiceCreateRequest) {
        return invoiceService.buyGymPassByClient(invoiceCreateRequest);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/history-of-orders")
    public Page<InvoiceHistoryOfOrdersPassResponse> getHistoryOfOrders(@RequestParam int page, @RequestParam int size) {
        return invoiceService.getHistoryOfOrdersPass(page, size);
    }

    @PreAuthorize("hasAnyRole('CLIENT','ADMIN','RECEPTIONIST')")
    @PutMapping("/history-of-orders/cancellation-order")
    public void cancellationOrder(@RequestParam Long orderIdRequest) {
        invoiceService.cancellationOrder(orderIdRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @GetMapping("/pending-invoices")
    public Page<InvoicesPendingResponse> getPendingInvoices(@RequestParam int page, @RequestParam int size) {
        return invoiceService.getPendingInvoices(page, size);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @PutMapping("/history-of-orders/confirm-delivery-of-funds")
    public void confirmDeliveryOdFunds(@RequestParam Long orderIdRequest) {
        invoiceService.confirmDeliveryOfFunds(orderIdRequest);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/is-pending-invoice")
    public InvoiceCreateIsPendingInvoiceResponse isPendingInvoice() {
        return invoiceService.isPending();
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @PostMapping("/save-invoice")
    public void saveInvoiceByReceptionistOrAdmin(@RequestParam Long gymMembershipId,
                                                 @RequestParam String numberCard) {
        invoiceService.saveInvoiceByReceptionistOrAdmin(gymMembershipId, Long.valueOf(numberCard));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/invoice-paid")
    public InvoiceResponse getPaidInvoiceById(@RequestParam Long invoiceId) {
        return invoiceService.getPaidInvoiceById(invoiceId);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/data-transfer")
    public InvoiceDataTransferResponse getDataTransferByUnpaidInvoiceId(@RequestParam Long invoiceId) {
        return invoiceService.getDataTransferByUnpaidInvoiceId(invoiceId);
    }

}
