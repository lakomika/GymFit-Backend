package pl.lakomika.gymfit.services;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lakomika.gymfit.repository.UserAppRepository;
import pl.lakomika.gymfit.utils.PasswordOperation;
import pl.lakomika.gymfit.utils.Role;
import pl.lakomika.gymfit.utils.UserAppData;

@Service
public class UserAppServiceImpl implements UserAppService {

    private final UserAppRepository userAppRepository;


    public UserAppServiceImpl(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }


    @Override
    public ResponseEntity<?> updateReceptionistPassword(String updatePassword, Long idReceptionist) {
        val passwordOperation = new PasswordOperation();
        passwordOperation.hashPassword(updatePassword);
        userAppRepository.updateUserPassword(passwordOperation.getHashPassword(), idReceptionist);
        return ResponseEntity.ok().build();
    }


    @Override
    @SneakyThrows
    public ResponseEntity<?> deactivationReceptionistAccount(Long idReceptionistRequest) {
        val user = userAppRepository.getById(idReceptionistRequest);
        if (user.getRole().equals(Role.RECEPTIONIST.getValue())) {
            userAppRepository.changeUserStatus(false, idReceptionistRequest);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("NO AUTHORISATION");
        }
    }

    @Override
    public ResponseEntity<?> deactivationClientAccount(Long idClientRequest) {
        val user = userAppRepository.getById(idClientRequest);
        if (user.getRole().equals(Role.CLIENT.getValue())) {
            userAppRepository.changeUserStatus(false, idClientRequest);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("NO AUTHORISATION");
        }

    }

    @Override
    public ResponseEntity<?> changePasswordByUser(String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            return ResponseEntity.badRequest().body("OLD AND NEW PASSWORD ARE THE SAME");
        }
        val user = userAppRepository.getById(UserAppData.getId());
        val bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userAppRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("OLD PASSWORD IS INCORRECT");
        }

    }

}
