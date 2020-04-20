package pl.lakomika.gymfit.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.lakomika.gymfit.entity.PasswordReset;
import pl.lakomika.gymfit.entity.UserApp;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface PasswordResetRepository extends PagingAndSortingRepository<PasswordReset, Long> {

    @Query("SELECT count(p)>0 from PasswordReset p where p.token= ?1 and p.status= ?2")
    boolean existsByTokenAndStatus(String token, boolean status);

    @Transactional
    @Modifying
    @Query("update PasswordReset p set p.status=false where p.user.id= ?1")
    void cancelAllTokenByUserId(Long id);

    @Transactional
    @Modifying
    @Query("update PasswordReset p set p.status=false where p.token= ?1 and p.user.id = ?2")
    void cancelTokenByName(String token, Long userAppId);

    @Query("SELECT p.user from PasswordReset p where p.token= ?1")
    UserApp getUserByToken(String token);

    @Query("SELECT p.dateCreate from PasswordReset p where p.token= ?1")
    Date getTokenDateCreate(String token);
}
