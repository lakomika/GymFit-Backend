package pl.lakomika.gymfit.DTO.client;

import lombok.Data;
import pl.lakomika.gymfit.entity.Client;

@Data
public class ClientCreateByReceptionistOrAdminRequest {
    private String email;

    private Client client;

}
