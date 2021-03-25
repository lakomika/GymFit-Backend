package pl.lakomika.gymfit.services;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.lakomika.gymfit.entity.PasswordReset;
import pl.lakomika.gymfit.repository.PasswordResetRepository;
import pl.lakomika.gymfit.repository.UserAppRepository;
import pl.lakomika.gymfit.utils.PasswordOperation;

import java.util.Date;
import java.util.UUID;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordResetRepository passwordResetRepository;
    private final UserAppRepository userAppRepository;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    @Value("${gymfit.app.frontend.url}")
    private String urlFrontend;

    public PasswordServiceImpl(PasswordResetRepository passwordResetRepository, UserAppRepository userAppRepository,
                               EmailService emailService, TemplateEngine templateEngine) {
        this.passwordResetRepository = passwordResetRepository;
        this.userAppRepository = userAppRepository;
        this.emailService = emailService;
        this.templateEngine = templateEngine;
    }

    @Override
    public Boolean isTokenValid(String token) {
        if (passwordResetRepository.existsByTokenAndStatus(token, true)) {
            val dateCreate = passwordResetRepository.getTokenDateCreate(token);
            val dateCreatePlusOneDay = new Date(dateCreate.getTime() + (1000 * 60 * 60 * 24));
            val currentDate = new Date();
            return dateCreatePlusOneDay.after(currentDate);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token in database");
        }
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        val user = passwordResetRepository.getUserByToken(token);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error token");
        }
        isTokenValid(token);
        val passwordOperation = new PasswordOperation();
        passwordOperation.hashPassword(newPassword);
        user.setPassword(passwordOperation.getHashPassword());
        userAppRepository.save(user);
        passwordResetRepository.cancelTokenByName(token, user.getId());
    }

    @Override
    public void sendEmailToResetPassword(String email) {
        if (!userAppRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email no exits in database");
        }
        String token = UUID.randomUUID().toString();
        val user = userAppRepository.getUserByEmail(email);
        val passwordResetToken = PasswordReset.builder()
                .token(token)
                .user(user)
                .status(true)
                .dateCreate(new Date())
                .build();
        passwordResetRepository.cancelAllTokenByUserId(user.getId());
        passwordResetRepository.save(passwordResetToken);
        val context = new Context();
        context.setVariable("link", urlFrontend + "/reset-password/" + token);
        val body = templateEngine.process("resetPasswordPL", context);
        emailService.sendEmail(email, "Resetowanie hasła", body);
    }

}
