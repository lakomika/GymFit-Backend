package pl.lakomika.gymfit.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "receptionist_data")
@Data
@NoArgsConstructor
public class Receptionist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 9, nullable = false)
    private int phoneNumber;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String street;

    @NotNull
    private String postcode;

    @NotNull
    private String city;

}
