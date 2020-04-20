package pl.lakomika.gymfit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lakomika.gymfit.repository.UserAppRepository;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@Builder
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @Column(unique = true)
    private String username;

    @NotNull
    private boolean active;

    @NotNull
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    private Administrator administrator;

    @OneToOne(cascade = CascadeType.ALL)
    private Receptionist receptionist;


    public UserApp(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserApp(Long id) {
    }

    public static String createLogin(String name, String surname, UserAppRepository userAppRepository) {
        String nameWithoutSpace = name.replaceAll("\\s+", "").toLowerCase();
        String surnameWithoutSpace = surname.replaceAll("\\s+", "").toLowerCase();
        int counter = 0;
        String newLogin = nameWithoutSpace + "." + surnameWithoutSpace;
        while (true) {
            if (counter == 0) {
                if (userAppRepository.existsByUsername(newLogin)) {
                    counter++;
                } else return newLogin;
            } else if (counter > 0) {
                if (userAppRepository.existsByUsername(newLogin + counter)) {
                    counter++;
                } else return newLogin + counter;
            }

        }
    }
}
