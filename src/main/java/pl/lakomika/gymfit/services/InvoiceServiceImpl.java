package pl.lakomika.gymfit.services;

import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.lakomika.gymfit.DTO.invoice.*;
import pl.lakomika.gymfit.entity.GymMembership;
import pl.lakomika.gymfit.entity.UserApp;
import pl.lakomika.gymfit.entity.invoice.Invoice;
import pl.lakomika.gymfit.entity.invoice.InvoiceClient;
import pl.lakomika.gymfit.entity.invoice.InvoiceCompanyDetails;
import pl.lakomika.gymfit.entity.invoice.InvoiceGymMembership;
import pl.lakomika.gymfit.repository.*;
import pl.lakomika.gymfit.utils.InvoiceStatus;
import pl.lakomika.gymfit.utils.TypeOfTransaction;
import pl.lakomika.gymfit.utils.UserAppData;

import java.util.Calendar;
import java.util.Date;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserAppRepository userAppRepository;
    private final CompanyDetailsRepository companyDetailsRepository;
    private final ClientRepository clientRepository;
    private final GymMembershipRepository gymMembershipRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserAppRepository userAppRepository,
                              CompanyDetailsRepository companyDetailsRepository, ClientRepository clientRepository,
                              GymMembershipRepository gymMembershipRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userAppRepository = userAppRepository;
        this.companyDetailsRepository = companyDetailsRepository;
        this.clientRepository = clientRepository;
        this.gymMembershipRepository = gymMembershipRepository;
    }


    @Override
    public ResponseEntity<?> buyGymPassByClient(InvoiceCreateRequest request) {
        val gymMembership = gymMembershipRepository.getActiveGymPassById(request.getGymMembershipId());
        val user = userAppRepository.getById(UserAppData.getId());
        val invoice = createInvoice(request, user,
                TypeOfTransaction.BANK_TRANSFER.getValue(), gymMembership);
        val newInvoice = invoiceRepository.save(invoice);
        val dataTransfer = InvoiceDataTransferResponse.toResponse(newInvoice);
        return ResponseEntity.ok(dataTransfer);
    }


    private Invoice createInvoice(InvoiceCreateRequest request, UserApp user,
                                  String typeOfTransaction, GymMembership gymMembership) {
        val gymMembershipInvoice = InvoiceGymMembership.fromGymMembership(gymMembership);
        val clientData = InvoiceClient
                .builder()
                .name(request.getClient().getName())
                .surname(request.getClient().getSurname())
                .phoneNumber(request.getClient().getPhoneNumber())
                .street(request.getClient().getStreet())
                .postcode(request.getClient().getPostcode())
                .city(request.getClient().getCity())
                .build();
        return Invoice
                .builder()
                .clientData(clientData)
                .plannedStartOfThePass(request.getPlannedStartOfThePass())
                .invoiceGymMembership(gymMembershipInvoice)
                .invoiceCompanyDetails(InvoiceCompanyDetails.fromCompanyDetails(
                        companyDetailsRepository.findById(companyDetailsRepository.getMaxId())))
                .status(InvoiceStatus.UNPAID.getValue())
                .userAppClient(user)
                .typeOfTransaction(typeOfTransaction)
                .dateCreate(new Date())
                .build();
    }


    @Override
    public Page<InvoiceHistoryOfOrdersPassResponse> getHistoryOfOrdersPass(int page, int size) {
        val pageRequest = PageRequest.of(page, size);
        val pageResult = invoiceRepository.getHistoryOfOrdersPassByUserAppId(UserAppData.getId(), pageRequest);
        val historyOfOrders = pageResult.getContent();
        return new PageImpl<>(InvoiceHistoryOfOrdersPassResponse.toResponse(historyOfOrders), pageRequest, pageResult.getTotalElements());
    }

    @Override
    public ResponseEntity<?> cancellationOrder(Long idOrder) {
        invoiceRepository.cancellationOrder(InvoiceStatus.CANCELLED.getValue(), idOrder);
        return ResponseEntity.ok().build();
    }

    @Override
    public Page<InvoicesPendingResponse> getPendingInvoices(int page, int size) {
        val pageRequest = PageRequest.of(page, size);
        val pageResult = invoiceRepository.getPendingInvoices(InvoiceStatus.UNPAID.getValue(), pageRequest);
        val pendingInvoices = pageResult.getContent();
        val response = InvoicesPendingResponse.toResponse(pendingInvoices);
        return new PageImpl<>(response, pageRequest, pageResult.getTotalElements());
    }

    @Override
    public ResponseEntity<?> confirmDeliveryOfFunds(Long idInvoice) {
        val invoice = invoiceRepository.findById(idInvoice).get();
        invoice.setStatus(InvoiceStatus.PAID.getValue());
        val now = new Date();
        if (now.after(invoice.getPlannedStartOfThePass())) {
            invoice.setStartOfThePass(now);
        } else {
            invoice.setStartOfThePass(invoice.getPlannedStartOfThePass());
        }
        Calendar dateCount = Calendar.getInstance();
        dateCount.setTime(invoice.getStartOfThePass());
        dateCount.set(Calendar.MILLISECOND, 0);
        dateCount.set(Calendar.SECOND, 0);
        dateCount.set(Calendar.MINUTE, 0);
        dateCount.set(Calendar.HOUR, 0);
        invoice.setStartOfThePass(dateCount.getTime());
        dateCount.add(Calendar.DATE, invoice.getInvoiceGymMembership().getNumberOfMonths() * 31);
        invoice.setEndOfThePass(dateCount.getTime());
        invoiceRepository.save(invoice);
        clientRepository.updateEndOfThePassClient(invoice.getEndOfThePass(), invoice.getUserAppClient().getClient().getId());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> isPending() {
        if (invoiceRepository.countStatus(UserAppData.getId(), InvoiceStatus.UNPAID.getValue()) >= 1) {
            return ResponseEntity.ok(new InvoiceCreateIsPendingInvoiceResponse(true));
        } else {
            return ResponseEntity.ok(new InvoiceCreateIsPendingInvoiceResponse(false));
        }
    }

    @Override
    public ResponseEntity<?> saveInvoiceByReceptionistOrAdmin(Long gymMembershipId, Long numberCard) {
        val userAppClient = userAppRepository.getUserClientByNumberCard(numberCard);
        val gymMembership = gymMembershipRepository.findById(gymMembershipId).get();
        val invoiceClientData = requestDataIsTheSameLikeInDatabase(userAppClient, gymMembershipId);
        val invoice = createInvoice(invoiceClientData, userAppClient,
                TypeOfTransaction.IN_CLUB.getValue(), gymMembership);
        val invoiceSavedId = invoiceRepository.save(invoice).getId();
        confirmDeliveryOfFunds(invoiceSavedId);
        return ResponseEntity.ok().build();
    }

    private InvoiceCreateRequest requestDataIsTheSameLikeInDatabase(UserApp userApp, Long gymMembershipId) {
        val modelMapper = new ModelMapper();
        val clientData = modelMapper.map(userApp.getClient(), InvoiceCreateClientDataRequest.class);
        val time = new Date().getTime();
        val plannedStartOfThePass = new Date();
        val currentDayOnMidnight = new Date(time - time % (24 * 60 * 60 * 1000));
        if (userApp.getClient().getEndOfThePass().after(currentDayOnMidnight)) {
            plannedStartOfThePass.setTime(userApp.getClient().getEndOfThePass().getTime());
        } else {
            plannedStartOfThePass.setTime(currentDayOnMidnight.getTime());
        }
        return InvoiceCreateRequest
                .builder()
                .client(clientData)
                .gymMembershipId(gymMembershipId)
                .plannedStartOfThePass(plannedStartOfThePass)
                .build();
    }

    @Override
    public ResponseEntity<?> getPaidInvoiceById(Long invoiceId) {
        val invoice = invoiceRepository.getInvoiceById(invoiceId);
        if (invoice.getUserAppClient().getId() != UserAppData.getId() || !invoice.getStatus().equals(InvoiceStatus.PAID.getValue())) {
            return ResponseEntity.badRequest().body("Invalid invoice id");
        } else {
            val response = InvoiceResponse.toResponse(invoice);
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<?> getDataTransferByUnpaidInvoiceId(Long invoiceId) {
        val invoice = invoiceRepository.getInvoiceById(invoiceId);
        if (invoice.getUserAppClient().getId() != UserAppData.getId() || !invoice.getStatus().equals(InvoiceStatus.UNPAID.getValue())) {
            return ResponseEntity.badRequest().body("Invalid invoice id");
        } else {
            val response = InvoiceDataTransferResponse.toResponse(invoice);
            return ResponseEntity.ok(response);
        }
    }
}

