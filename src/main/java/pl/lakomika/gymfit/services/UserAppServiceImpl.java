package pl.lakomika.gymfit.services;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
    public void updateReceptionistPassword(String updatePassword, Long idReceptionist) {
        val passwordOperation = new PasswordOperation();
        passwordOperation.hashPassword(updatePassword);
        userAppRepository.updateUserPassword(passwordOperation.getHashPassword(), idReceptionist);
    }


    @Override
    @SneakyThrows
    public void deactivationReceptionistAccount(Long idReceptionistRequest) {
        val user = userAppRepository.getById(idReceptionistRequest);
        if (!user.getRole().equals(Role.RECEPTIONIST.getValue())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Authorisation");
        }
        userAppRepository.changeUserStatus(false, idReceptionistRequest);
    }

    @Override
    public void deactivationClientAccount(Long idClientRequest) {
        val user = userAppRepository.getById(idClientRequest);
        if (!user.getRole().equals(Role.CLIENT.getValue())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Authorisation");
        }
        userAppRepository.changeUserStatus(false, idClientRequest);
    }

    @Override
    public void changePasswordByUser(String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old and new password are the same");
        }
        val user = userAppRepository.getById(UserAppData.getId());
        val bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userAppRepository.save(user);
    }

}
