package pl.lakomika.gymfit.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lakomika.gymfit.DTO.client.ClientCreateResponse;
import pl.lakomika.gymfit.DTO.receptionist.ReceptionistCreateRequest;
import pl.lakomika.gymfit.DTO.receptionist.UserAppReceptionistDTO;
import pl.lakomika.gymfit.services.ReceptionistService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/receptionist/")
public class ReceptionistController {
    private final ReceptionistService receptionistService;

    @Autowired
    public ReceptionistController(ReceptionistService receptionistService) {
        this.receptionistService = receptionistService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-receptionist")
    public ClientCreateResponse addReceptionistByAdmin(@Valid @RequestBody ReceptionistCreateRequest receptionistCreateRequest) {
        return receptionistService.addReceptionistByAdmin(receptionistCreateRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-receptionists-by-status")
    public Page<UserAppReceptionistDTO> getReceptionistsByStatus(@RequestParam int page, @RequestParam int size,
                                                                 @RequestParam boolean isActive) {
        return receptionistService.getReceptionistByStatus(page, size, isActive);
    }

}
