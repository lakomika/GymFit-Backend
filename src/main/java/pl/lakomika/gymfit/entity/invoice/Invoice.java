package pl.lakomika.gymfit.entity.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lakomika.gymfit.entity.UserApp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private UserApp userAppClient;

    @NotNull
    private Date plannedStartOfThePass;

    private Date startOfThePass;

    private Date endOfThePass;

    @NotNull
    private Date dateCreate;

    @NotNull
    private String status;

    @NotNull
    private String typeOfTransaction;

    @OneToOne(cascade = {CascadeType.ALL})
    private InvoiceGymMembership invoiceGymMembership;

    @OneToOne(cascade = {CascadeType.ALL})
    private InvoiceCompanyDetails invoiceCompanyDetails;

    @OneToOne(cascade = {CascadeType.ALL})
    private InvoiceClient clientData;
}
