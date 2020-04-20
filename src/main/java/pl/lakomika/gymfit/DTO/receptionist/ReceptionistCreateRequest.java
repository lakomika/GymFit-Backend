package pl.lakomika.gymfit.DTO.receptionist;

import lombok.Data;
import pl.lakomika.gymfit.entity.Receptionist;

@Data
public class ReceptionistCreateRequest {
    private String email;

    private Receptionist receptionist;
}
