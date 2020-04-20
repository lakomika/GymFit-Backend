package pl.lakomika.gymfit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.lakomika.gymfit.entity.GymMembership;

import javax.transaction.Transactional;

@Repository
public interface GymMembershipRepository extends PagingAndSortingRepository<GymMembership, Long> {

    @Query("SELECT g from GymMembership g where g.active= ?1 order by g.id asc")
    Page<GymMembership> findAllByStatus(boolean status, PageRequest pageable);

    @Transactional
    @Modifying
    @Query("update GymMembership g set g.active= ?1 where g.id= ?2")
    void changeStatus(boolean status, Long id);

    @Query("SELECT g from GymMembership g where g.active= true and g.id = ?1")
    GymMembership getActiveGymPassById(Long id);

    @Override
    Iterable<GymMembership> findAllById(Iterable<Long> longs);

    @Override
    <S extends GymMembership> S save(S entity);
}

