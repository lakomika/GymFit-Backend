package pl.lakomika.gymfit.services;

import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.lakomika.gymfit.DTO.client.ClientCreateResponse;
import pl.lakomika.gymfit.DTO.receptionist.ReceptionistCreateRequest;
import pl.lakomika.gymfit.DTO.receptionist.UserAppReceptionistDTO;
import pl.lakomika.gymfit.entity.Receptionist;
import pl.lakomika.gymfit.entity.UserApp;
import pl.lakomika.gymfit.repository.ReceptionistRepository;
import pl.lakomika.gymfit.repository.UserAppRepository;
import pl.lakomika.gymfit.utils.PasswordOperation;
import pl.lakomika.gymfit.utils.Role;

@Service
public class ReceptionistServiceImpl implements ReceptionistService {
    private final ReceptionistRepository receptionistRepository;
    private final UserAppRepository userAppRepository;

    public ReceptionistServiceImpl(ReceptionistRepository receptionistRepository, UserAppRepository userAppRepository) {
        this.receptionistRepository = receptionistRepository;
        this.userAppRepository = userAppRepository;
    }

    @Override
    public void update(Receptionist receptionist) {
        receptionistRepository.save(receptionist);
    }

    @Override
    public Page<UserAppReceptionistDTO> getReceptionistByStatus(int page, int size, boolean status) {
        val pageRequest = PageRequest.of(page, size);
        val pageResult = userAppRepository.getActiveUserByRole(status, Role.RECEPTIONIST.getValue(), pageRequest);
        val receptionistActive = pageResult.getContent();
        return new PageImpl<>(UserAppReceptionistDTO.userAppListToAppReceptionistListDTO(receptionistActive), pageRequest, pageResult.getTotalElements());
    }

    @Override
    public ClientCreateResponse addReceptionistByAdmin(ReceptionistCreateRequest receptionistCreateRequest) {

        val modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        val userAppReceptionist = modelMapper.map(receptionistCreateRequest, UserApp.class);

        String errorMessage = "";
        boolean isError = false;
        if (userAppRepository.existsByEmail(userAppReceptionist.getEmail())) {
            isError = true;
            errorMessage += " `email` ";
        }
        if (receptionistRepository.existsByPhoneNumber(userAppReceptionist.getReceptionist().getPhoneNumber())) {
            isError = true;
            errorMessage += " `numer telefonu` ";

        }
        if (isError) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dane takie jak" + errorMessage + " występują już w bazie danych!");
        } else {
            PasswordOperation passwordOperation = new PasswordOperation(10);
            userAppReceptionist.setRole(Role.RECEPTIONIST.getValue());
            userAppReceptionist.setPassword(passwordOperation.getHashPassword());
            userAppReceptionist.setUsername(UserApp.createLogin(userAppReceptionist.getReceptionist().getName(), userAppReceptionist.getReceptionist().getSurname(), userAppRepository));
            userAppReceptionist.setActive(true);
            userAppReceptionist.setClient(null);
            userAppRepository.save(userAppReceptionist);
            return new ClientCreateResponse(null, userAppReceptionist.getUsername(), passwordOperation.getPassword(), "Success");
        }
    }
}

