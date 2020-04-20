package pl.lakomika.gymfit.DTO.client;

import lombok.Data;
import org.modelmapper.ModelMapper;
import pl.lakomika.gymfit.entity.Client;

import java.util.Date;

@Data
public class ClientDataAboutGymPassResponse {
    private String name;

    private String surname;

    private Date endOfThePass;

    private String status;


    public static ClientDataAboutGymPassResponse transferData(Client client) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(client, ClientDataAboutGymPassResponse.class);
    }
}
