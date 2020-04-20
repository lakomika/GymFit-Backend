package pl.lakomika.gymfit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lakomika.gymfit.services.UserAppService;

@CrossOrigin
@RestController
@RequestMapping("/api/user/")
public class UserController {
    private final UserAppService userAppService;

    @Autowired
    public UserController(UserAppService userAppService) {
        this.userAppService = userAppService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deactivation-account-receptionist")
    public ResponseEntity<?> deactivationReceptionistAccount(@RequestParam Long idReceptionistRequest) {
        return userAppService.deactivationReceptionistAccount(idReceptionistRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @PutMapping("/deactivation-account-client")
    public ResponseEntity<?> deactivationClientAccount(@RequestParam Long idClientRequest) {
        return userAppService.deactivationClientAccount(idClientRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-password-receptionist")
    public ResponseEntity<?> changeSelectedPasswordReceptionist(@RequestParam String updatePassword,
                                                                @RequestParam Long idReceptionist) {
        return userAppService.updateReceptionistPassword(updatePassword, idReceptionist);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','CLIENT')")
    @PutMapping("/change-password")
    public ResponseEntity<?> changePasswordByUser(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return userAppService.changePasswordByUser(oldPassword, newPassword);
    }


}
