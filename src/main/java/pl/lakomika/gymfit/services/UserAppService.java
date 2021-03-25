package pl.lakomika.gymfit.services;

public interface UserAppService {

    void updateReceptionistPassword(String updatePasswordRequest, Long idReceptionistRequest);

    void deactivationReceptionistAccount(Long idReceptionistRequest);

    void deactivationClientAccount(Long idClientRequest);

    void changePasswordByUser(String oldPassword, String newPassword);

}
