package pl.lakomika.gymfit.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lakomika.gymfit.DTO.gymMembership.GymMemberShipsResponse;
import pl.lakomika.gymfit.DTO.gymMembership.GymMembershipAddRequest;
import pl.lakomika.gymfit.services.GymMembershipService;

@CrossOrigin
@RestController
@RequestMapping("/api/gym-membership/")
public class GymMembershipController {
    private final GymMembershipService gymMembershipService;

    @Autowired
    public GymMembershipController(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-gym-membership")
    public ResponseEntity<?> addGymMembership(@RequestBody GymMembershipAddRequest gymMembership) {
        return gymMembershipService.save(gymMembership);
    }

    @PreAuthorize("hasAnyRole('CLIENT','ADMIN','RECEPTIONIST')")
    @GetMapping("/active-gym-memberships")
    public Page<GymMemberShipsResponse> getActiveGymMemberships(@RequestParam int page, @RequestParam int size) {
        return gymMembershipService.findAllByStatus(page, size, true);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactive-gym-memberships")
    public Page<GymMemberShipsResponse> inactiveGymMemberships(@RequestParam int page, @RequestParam int size) {
        return gymMembershipService.findAllByStatus(page, size, false);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deactivation-gym-membership")
    public ResponseEntity<?> deactivationGymMembership(@RequestParam Long gymMembershipIdRequest) {
        return gymMembershipService.changeStatus(gymMembershipIdRequest, false);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/get-active-gym-pass-by-id")
    public ResponseEntity<?> getActiveGymPassById(@RequestParam Long id) {
        return gymMembershipService.getActiveGymPassById(id);
    }
}
