package pl.lakomika.gymfit.DTO.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lakomika.gymfit.entity.Client;
import pl.lakomika.gymfit.entity.UserApp;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAppClientDTO {
    private Long id;

    private String email;

    private String username;

    private Client client;

    private static UserAppClientDTO userAppToAppClientDTO(UserApp userApp) {
        return new UserAppClientDTO(userApp.getId(), userApp.getEmail(), userApp.getUsername(), userApp.getClient());
    }

    public static List<UserAppClientDTO> userAppListToAppReceptionistListDTO(List<UserApp> userAppList) {
        ArrayList<UserAppClientDTO> userAppReceptionistDTOList = new ArrayList<>();
        for (UserApp userApp : userAppList) {
            userAppReceptionistDTOList.add(userAppToAppClientDTO(userApp));
        }
        return userAppReceptionistDTOList;
    }


}
