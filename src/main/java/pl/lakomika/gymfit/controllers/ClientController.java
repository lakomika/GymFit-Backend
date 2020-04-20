package pl.lakomika.gymfit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lakomika.gymfit.DTO.client.*;
import pl.lakomika.gymfit.services.ClientService;

import javax.validation.Valid;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/client/")
public class ClientController extends BaseController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','CLIENT')")
    @GetMapping("/get-personal-data")
    public ResponseEntity<?> getPersonalDataClient() {
        return ResponseEntity.ok(clientService.getPersonalData());
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @GetMapping("/get-clients-by-status")
    public Page<UserAppClientDTO> getClientsByStatus(@RequestParam int page, @RequestParam int size,
                                                     @RequestParam boolean isActive) {
        return clientService.getClientsByStatus(page, size, isActive);
    }


    @PostMapping("/add-client-on-the-public-content")
    public ResponseEntity<?> registerClientOnThePublicContent(
            @Valid @RequestBody ClientCreateOnThePublicContentRequest client) {
        return clientService.registerClientOnThePublicContent(client);
    }


    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @PostMapping("/add-client")
    public ResponseEntity<?> registerUserByReceptionistOrAdmin(
            @Valid @RequestBody ClientCreateByReceptionistOrAdminRequest clientCreateByReceptionistOrAdmin) {
        return clientService.saveClientByReceptionistOrAdmin(clientCreateByReceptionistOrAdmin);
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @GetMapping("/get-number-access-card")
    public ResponseEntity<?> getNumberAccessCard() {
        return clientService.getNumberAccessCard();

    }

    @PreAuthorize("hasAnyRole('RECEPTIONIST','ADMIN')")
    @GetMapping("/get-data-about-gym-pass")
    public ResponseEntity<?> getDataClientAboutGymPass(@RequestParam(value = "numberCard") String numberCard) {
        return clientService.getDataClientAboutGymPass(Long.valueOf(numberCard));
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @GetMapping("/get-client-basic-data")
    public ResponseEntity<?> getBasicDataClient() {
        return clientService.getBasicDataClient();
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @GetMapping("/get-client-profile")
    public ClientProfileResponse getClientProfile() {
        return clientService.getProfile();
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @PutMapping("/edit-client-data")
    public void editClientProfile(@Valid ClientUpdateDataRequest clientUpdateData) {
        clientService.editClientProfile(clientUpdateData);
    }


    @PreAuthorize("hasAnyRole('CLIENT')")
    @GetMapping("/get-date-of-end-gym-pass")
    public ResponseEntity<Date> getClientDateOfEndGymPass() {
        return clientService.getClientDateOfEndGymPass();
    }


}
