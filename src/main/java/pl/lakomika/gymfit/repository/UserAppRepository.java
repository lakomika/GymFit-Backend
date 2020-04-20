package pl.lakomika.gymfit.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.lakomika.gymfit.entity.UserApp;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserAppRepository extends PagingAndSortingRepository<UserApp, Long> {

    @Query("select u from UserApp u where u.active=true and u.username=?1")
    Optional<UserApp> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    UserApp getById(Long Id);

    @Query("select u from UserApp u where u.active= ?1 and u.role= ?2 order by u.id asc")
    Page<UserApp> getActiveUserByRole(boolean isActive, String role, PageRequest pageable);

    @Query("SELECT u.id from UserApp u where u.username= ?1")
    Long getByIdFromUsername(String username);

    @Transactional
    @Modifying
    @Query("update UserApp u set u.password= ?1 where u.id= ?2")
    void updateUserPassword(String password, Long id);

    @Transactional
    @Modifying
    @Query("update UserApp u set u.active= ?1 where u.id= ?2")
    void changeUserStatus(boolean isActive, Long id);

    @Query("select u from UserApp u where u.username= ?1")
    UserApp getClientPersonalData(String username);

    @Query("SELECT u.client.numberCard from UserApp u where u.id= ?1")
    Long getNumberAccessCardByUserAppId(Long id);

    @Query("SELECT u.email from UserApp u where u.id= ?1")
    String getEmailByUserAppId(Long id);

    @Query("SELECT u from UserApp u where u.email= ?1")
    UserApp getUserByEmail(String email);

    @Query("SELECT u from UserApp u where u.client.numberCard= ?1")
    UserApp getUserClientByNumberCard(Long numberCard);
}

