package pl.lakomika.gymfit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String token;

    @NotNull
    @OneToOne(targetEntity = UserApp.class, fetch = FetchType.EAGER)
    private UserApp user;

    @NotNull
    private Date dateCreate;

    @NotNull
    private Boolean status;

}
