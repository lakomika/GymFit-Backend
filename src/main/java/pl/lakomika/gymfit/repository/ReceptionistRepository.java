package pl.lakomika.gymfit.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.lakomika.gymfit.entity.Receptionist;

@Repository
public interface ReceptionistRepository extends PagingAndSortingRepository<Receptionist, Long> {
    Boolean existsByPhoneNumber(int phoneNumber);

}
