package pl.lakomika.gymfit.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pl.lakomika.gymfit.DTO.receptionist.ReceptionistCreateRequest;
import pl.lakomika.gymfit.DTO.receptionist.UserAppReceptionistDTO;
import pl.lakomika.gymfit.entity.Receptionist;

public interface ReceptionistService {
    void update(Receptionist receptionist);

    Page<UserAppReceptionistDTO> getReceptionistByStatus(int page, int size, boolean status);

    ResponseEntity<?> addReceptionistByAdmin(ReceptionistCreateRequest receptionistCreateRequest);
}
