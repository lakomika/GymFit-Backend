package pl.lakomika.gymfit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void deactivationReceptionistAccount(@RequestParam Long idReceptionistRequest) {
        userAppService.deactivationReceptionistAccount(idReceptionistRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @PutMapping("/deactivation-account-client")
    public void deactivationClientAccount(@RequestParam Long idClientRequest) {
        userAppService.deactivationClientAccount(idClientRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-password-receptionist")
    public void changeSelectedPasswordReceptionist(@RequestParam String updatePassword,
                                                   @RequestParam Long idReceptionist) {
        userAppService.updateReceptionistPassword(updatePassword, idReceptionist);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','CLIENT')")
    @PutMapping("/change-password")
    public void changePasswordByUser(@RequestParam String oldPassword, @RequestParam String newPassword) {
        userAppService.changePasswordByUser(oldPassword, newPassword);
    }


}
