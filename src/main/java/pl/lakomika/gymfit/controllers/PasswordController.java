package pl.lakomika.gymfit.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lakomika.gymfit.services.PasswordService;

@CrossOrigin
@RestController
@RequestMapping("/api/password/")
public class PasswordController {
    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/is-token-exist")
    public ResponseEntity<?> isTokenValid(@RequestParam(name = "token") String token) {
        if (passwordService.isTokenValid(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/reset-password")
    public void isTokenValid(@RequestParam String token, @RequestParam String newPassword) {
        passwordService.resetPassword(token, newPassword);
    }

    @PostMapping("/send-email-to-reset-password")
    public void sendEmailToResetPassword(@RequestParam String email) {
        passwordService.sendEmailToResetPassword(email);
    }
}


