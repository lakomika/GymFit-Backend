package pl.lakomika.gymfit.services;

import org.springframework.data.domain.Page;
import pl.lakomika.gymfit.DTO.client.ClientCreateResponse;
import pl.lakomika.gymfit.DTO.receptionist.ReceptionistCreateRequest;
import pl.lakomika.gymfit.DTO.receptionist.UserAppReceptionistDTO;
import pl.lakomika.gymfit.entity.Receptionist;

public interface ReceptionistService {
    void update(Receptionist receptionist);

    Page<UserAppReceptionistDTO> getReceptionistByStatus(int page, int size, boolean status);

    ClientCreateResponse addReceptionistByAdmin(ReceptionistCreateRequest receptionistCreateRequest);
}
