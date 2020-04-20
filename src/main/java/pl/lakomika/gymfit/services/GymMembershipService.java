package pl.lakomika.gymfit.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pl.lakomika.gymfit.DTO.gymMembership.GymMemberShipsResponse;
import pl.lakomika.gymfit.DTO.gymMembership.GymMembershipAddRequest;
import pl.lakomika.gymfit.entity.GymMembership;

public interface GymMembershipService {
    ResponseEntity<?> save(GymMembershipAddRequest gymMembershipRequest);

    Page<GymMemberShipsResponse> findAllByStatus(int page, int size, boolean status);

    void update(GymMembership gymMembership);

    ResponseEntity<?> changeStatus(Long id, boolean status);

    ResponseEntity<?> getActiveGymPassById(Long id);
}
