package pl.lakomika.gymfit.services;

import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.lakomika.gymfit.DTO.client.*;
import pl.lakomika.gymfit.entity.Client;
import pl.lakomika.gymfit.entity.UserApp;
import pl.lakomika.gymfit.repository.ClientRepository;
import pl.lakomika.gymfit.repository.UserAppRepository;
import pl.lakomika.gymfit.utils.PasswordOperation;
import pl.lakomika.gymfit.utils.Role;
import pl.lakomika.gymfit.utils.UserAppData;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserAppRepository userAppRepository;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserAppRepository userAppRepository) {
        this.clientRepository = clientRepository;
        this.userAppRepository = userAppRepository;

    }

    @Override
    public ClientPersonalDataResponse getPersonalData() {
        val userApp = userAppRepository.getClientPersonalData(UserAppData.getUsername());
        val modelMapper = new ModelMapper();
        return modelMapper.map(userApp.getClient(), ClientPersonalDataResponse.class);
    }

    @Override
    public ClientProfileResponse getProfile() {
        val userApp = userAppRepository.getClientPersonalData(UserAppData.getUsername());
        val modelMapper = new ModelMapper();
        ClientProfileResponse clientProfileResponse = modelMapper.map(userApp.getClient(), ClientProfileResponse.class);
        clientProfileResponse.setEmail(userAppRepository.getEmailByUserAppId(UserAppData.getId()));
        return clientProfileResponse;
    }

    @Override
    public Page<UserAppClientDTO> getClientsByStatus(int page, int size, boolean isActive) {
        val pageRequest = PageRequest.of(page, size);
        val pageResult = userAppRepository.getActiveUserByRole(isActive, Role.CLIENT.getValue(), pageRequest);
        val clientActive = pageResult.getContent();
        return new PageImpl<>(UserAppClientDTO.userAppListToAppReceptionistListDTO(clientActive), pageRequest, pageResult.getTotalElements());
    }

    @Override
    public ClientCreateResponse registerClientOnThePublicContent(
            ClientCreateOnThePublicContentRequest requestDataClient) {

        val modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        val clientData = modelMapper.map(requestDataClient.getClient(), Client.class);
        String errorMessage = "";
        if (userAppRepository.existsByUsername(requestDataClient.getUsername())) {
            errorMessage += "'nazwa użytkownika' ";
        }
        if (userAppRepository.existsByEmail(requestDataClient.getEmail())) {
            errorMessage += " 'email' ";
        }
        if (clientRepository.existsByPhoneNumber(
                clientData.getPhoneNumber())) {
            errorMessage += " 'numer telefonu' ";

        }
        if (!errorMessage.equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dane takie jak"
                    + errorMessage +
                    "występują już w bazie danych!");
        } else {
            clientData.setEndOfThePass(null);
            clientData.setNumberCard(new Date().getTime());
            val bCryptPasswordEncoder = new BCryptPasswordEncoder();
            val hashPassword = bCryptPasswordEncoder.encode(requestDataClient.getPassword());
            val userSaving = UserApp.builder()
                    .username(requestDataClient.getUsername())
                    .email(requestDataClient.getEmail())
                    .password(hashPassword)
                    .client(clientData)
                    .role(Role.CLIENT.getValue())
                    .active(true)
                    .build();
            userAppRepository.save(userSaving);
            return new ClientCreateResponse("Rejestracja powiodła się!");

        }
    }

    @Override
    public ResponseEntity<?> saveClientByReceptionistOrAdmin(ClientCreateByReceptionistOrAdminRequest clientCreateByReceptionistOrAdmin) {

        val modelMapper = new ModelMapper();
        val userAppClient = modelMapper.map(clientCreateByReceptionistOrAdmin, UserApp.class);

        String errorMessage = "";
        if (userAppRepository.existsByEmail(userAppClient.getEmail())) {
            errorMessage += " `email` ";
        }
        if (clientRepository.existsByPhoneNumber(userAppClient.getClient().getPhoneNumber())) {
            errorMessage += " `numer telefonu` ";

        }
        if (!errorMessage.equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(new ClientCreateResponse("Dane takie jak" + errorMessage + " występują już w bazie danych!"));
        } else {
            PasswordOperation passwordOperation = new PasswordOperation(10);
            userAppClient.setRole(Role.CLIENT.getValue());
            userAppClient.setPassword(passwordOperation.getHashPassword());
            userAppClient.setUsername(UserApp.createLogin(userAppClient.getClient().getName(), userAppClient.getClient().getSurname(), userAppRepository));
            userAppClient.setActive(true);
            userAppClient.getClient().setNumberCard(new Timestamp(System.currentTimeMillis()).getTime());
            userAppRepository.save(userAppClient);
            userAppClient.setId(userAppRepository.getByIdFromUsername(userAppClient.getUsername()));
            return ResponseEntity.ok(
                    new ClientCreateResponse(userAppClient.getClient().getNumberCard(),
                            userAppClient.getUsername(),
                            passwordOperation.getPassword(), "Success"));
        }
    }

    @Override
    public ClientAccessCardResponse getNumberAccessCard() {
        return new ClientAccessCardResponse(userAppRepository.getNumberAccessCardByUserAppId(UserAppData.getId()));
    }

    @Override
    public ClientDataAboutGymPassResponse getDataClientAboutGymPass(Long numberCard) {
        val clientData = clientRepository.getDataAboutGymPass(numberCard);
        val clientDataAboutGymPassResponse = ClientDataAboutGymPassResponse.transferData(clientData);
        val dateHelper = Calendar.getInstance();
        setMidnightInHelper(dateHelper);
        val todayAtMidnight = dateHelper.getTime();

        if (isTheGymPassHasBeenEverBought(clientData)) {
            if (isGymPassIsValid(clientData, todayAtMidnight))
                clientDataAboutGymPassResponse.setStatus("VALID");
            else clientDataAboutGymPassResponse.setStatus("INVALID");
        } else clientDataAboutGymPassResponse.setStatus("INVALID");

        return clientDataAboutGymPassResponse;

    }

    private boolean isGymPassIsValid(Client clientData, Date todayAtMidnight) {
        return todayAtMidnight.before(clientData.getEndOfThePass()) || todayAtMidnight.equals(clientData.getEndOfThePass());
    }

    private boolean isTheGymPassHasBeenEverBought(Client clientData) {
        return clientData.getEndOfThePass() != null;
    }

    private void setMidnightInHelper(Calendar dateHelper) {
        dateHelper.set(Calendar.MILLISECOND, 0);
        dateHelper.set(Calendar.SECOND, 0);
        dateHelper.set(Calendar.MINUTE, 0);
        dateHelper.set(Calendar.HOUR, 0);
    }

    @Override
    public ClientDataAboutGymPassResponse getBasicDataClient() {
        val idUserApp = UserAppData.getId();
        val userAppClient = userAppRepository.getById(idUserApp);
        return getDataClientAboutGymPass(userAppClient.getClient().getNumberCard());
    }


    @Override
    public void editClientProfile(ClientUpdateDataRequest update) {
        val userData = userAppRepository.getById(UserAppData.getId());
        val clientUpdatingData = userData.getClient();
        userData.setEmail(update.getEmail());
        clientUpdatingData.setName(update.getName());
        clientUpdatingData.setSurname(update.getSurname());
        clientUpdatingData.setPhoneNumber(update.getPhoneNumber());
        clientUpdatingData.setStreet(update.getStreet());
        clientUpdatingData.setPostcode(update.getPostcode());
        clientUpdatingData.setCity(update.getCity());
        userAppRepository.save(userData);
    }

    @Override
    public Date getClientDateOfEndGymPass() {
        val userClient = userAppRepository.getById(UserAppData.getId());
        return userClient.getClient().getEndOfThePass();
    }
}
