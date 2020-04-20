package pl.lakomika.gymfit.entity;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "client_data")
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Column(unique = true, length = 9)
    private int phoneNumber;

    @NotNull
    private String street;

    @NotNull
    private String postcode;

    @NotNull
    private String city;

    @NotNull
    private Long numberCard;

    @NotNull
    private Date dateOfBirth;

    @Nullable
    private Date endOfThePass;

}
