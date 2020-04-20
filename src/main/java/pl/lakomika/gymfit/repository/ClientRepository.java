package pl.lakomika.gymfit.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.lakomika.gymfit.entity.Client;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Boolean existsByPhoneNumber(int phoneNumber);

    @Transactional
    @Modifying
    @Query("update Client c set c.endOfThePass= ?1 where c.id= ?2")
    void updateEndOfThePassClient(Date endOfThePass, Long id);

    @Query("SELECT c from Client c where c.numberCard= ?1")
    Client getDataAboutGymPass(long numberCard);

}
