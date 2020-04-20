package pl.lakomika.gymfit.DTO.receptionist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import pl.lakomika.gymfit.entity.Receptionist;
import pl.lakomika.gymfit.entity.UserApp;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAppReceptionistDTO {
    private Long id;

    private String email;

    private String username;

    private Receptionist receptionist;

    private static UserAppReceptionistDTO userAppToAppReceptionistDTO(UserApp userApp) {
        return new UserAppReceptionistDTO(userApp.getId(), userApp.getEmail(), userApp.getUsername(), userApp.getReceptionist());
    }

    public static List<UserAppReceptionistDTO> userAppListToAppReceptionistListDTO(List<UserApp> userAppList) {
        val userAppReceptionistDTOList = new ArrayList<UserAppReceptionistDTO>();
        for (UserApp userApp : userAppList) {
            userAppReceptionistDTOList.add(userAppToAppReceptionistDTO(userApp));
        }
        return userAppReceptionistDTOList;
    }


}
