package pl.lakomika.gymfit.services;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pl.lakomika.gymfit.DTO.client.*;

import java.util.Date;

public interface ClientService {
    ClientPersonalDataResponse getPersonalData();

    Page<UserAppClientDTO> getClientsByStatus(int page, int size, boolean isActive);

    ClientCreateResponse registerClientOnThePublicContent(
            ClientCreateOnThePublicContentRequest clientCreate);

    ClientAccessCardResponse getNumberAccessCard();

    ClientDataAboutGymPassResponse getDataClientAboutGymPass(Long numberCard);

    ResponseEntity<?> saveClientByReceptionistOrAdmin(ClientCreateByReceptionistOrAdminRequest clientCreateByReceptionistOrAdmin);

    ClientDataAboutGymPassResponse getBasicDataClient();

    ClientProfileResponse getProfile();

    void editClientProfile(ClientUpdateDataRequest clientUpdateData);

    Date getClientDateOfEndGymPass();
}
