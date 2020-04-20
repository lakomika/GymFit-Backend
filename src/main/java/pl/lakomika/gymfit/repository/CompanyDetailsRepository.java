package pl.lakomika.gymfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.lakomika.gymfit.entity.CompanyDetails;

import java.util.Optional;

@Repository
public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {

    @Query("SELECT c from CompanyDetails c where c.id= ?1")
    Optional<CompanyDetails> findById(Long id);

    @Query("SELECT coalesce(max(c.id), 0) FROM CompanyDetails c")
    Long getMaxId();
}
