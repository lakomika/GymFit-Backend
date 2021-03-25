package pl.lakomika.gymfit.services;

import org.springframework.data.domain.Page;
import pl.lakomika.gymfit.DTO.gymMembership.GymMemberShipAllDataResponse;
import pl.lakomika.gymfit.DTO.gymMembership.GymMemberShipsResponse;
import pl.lakomika.gymfit.DTO.gymMembership.GymMembershipAddRequest;
import pl.lakomika.gymfit.entity.GymMembership;

public interface GymMembershipService {
    void save(GymMembershipAddRequest gymMembershipRequest);

    Page<GymMemberShipsResponse> findAllByStatus(int page, int size, boolean status);

    void update(GymMembership gymMembership);

    void changeStatus(Long id, boolean status);

    GymMemberShipAllDataResponse getActiveGymPassById(Long id);
}
