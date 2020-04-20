package pl.lakomika.gymfit.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.lakomika.gymfit.entity.invoice.Invoice;

import javax.transaction.Transactional;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {

    @Query("SELECT i from Invoice i where i.userAppClient.id= ?1 order by i.id asc")
    Page<Invoice> getHistoryOfOrdersPassByUserAppId(Long id, PageRequest pageable);

    @Transactional
    @Modifying
    @Query("update Invoice i set i.status= ?1 where i.id= ?2")
    void cancellationOrder(String status, Long idOrder);

    @Query("SELECT i from Invoice i where i.status= ?1 order by i.id asc")
    Page<Invoice> getPendingInvoices(String status, PageRequest pageRequest);

    @Query("SELECT count(i) from Invoice i where i.userAppClient.id= ?1 and i.status= ?2")
    int countStatus(Long id, String status);

    Invoice getInvoiceById(Long invoiceId);
}

