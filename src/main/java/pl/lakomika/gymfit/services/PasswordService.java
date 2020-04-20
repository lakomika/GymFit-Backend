package pl.lakomika.gymfit.services;

public interface PasswordService {
    Boolean isTokenValid(String token);

    void resetPassword(String token, String newPassword);

    void sendEmailToResetPassword(String email);
}
