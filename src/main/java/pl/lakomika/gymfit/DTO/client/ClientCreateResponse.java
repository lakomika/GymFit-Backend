package pl.lakomika.gymfit.DTO.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientCreateResponse {
    private Long numberCard;

    private String username;

    private String password;

    private String message;

    public ClientCreateResponse(String message) {
        this.message = message;
    }
}
