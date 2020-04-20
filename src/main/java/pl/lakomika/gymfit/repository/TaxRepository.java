package pl.lakomika.gymfit.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.lakomika.gymfit.entity.Tax;

public interface TaxRepository extends PagingAndSortingRepository<Tax, Long> {

    @Query("SELECT t FROM Tax t where t.id = (select coalesce(max(t.id), 0) from Tax t)")
    Tax getTaxRateByMaxId();
}
