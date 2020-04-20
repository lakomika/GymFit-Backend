package pl.lakomika.gymfit.services;

import org.springframework.http.ResponseEntity;

public interface UserAppService {

    ResponseEntity<?> updateReceptionistPassword(String updatePasswordRequest, Long idReceptionistRequest);

    ResponseEntity<?> deactivationReceptionistAccount(Long idReceptionistRequest);

    ResponseEntity<?> deactivationClientAccount(Long idClientRequest);

    ResponseEntity<?> changePasswordByUser(String oldPassword, String newPassword);

}
