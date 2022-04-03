package pl.kcit.tof.client.serivces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kcit.tof.client.ClientContactDataRequest;
import pl.kcit.tof.client.dto.ClientAuthRequest;
import pl.kcit.tof.configuration.exception.TofInternalException;

@Slf4j
@Service
public class ClientService {

    private final ClientAuthService clientAuthService;
    private final ClientDataService clientDataService;

    public ClientService(ClientAuthService clientAuthService, ClientDataService clientDataService) {
        this.clientAuthService = clientAuthService;
        this.clientDataService = clientDataService;
    }

    public void register(ClientAuthRequest clientAuthRequest) throws TofInternalException {
        clientAuthService.registerClient(clientAuthRequest);
    }

    public void login(ClientAuthRequest clientAuthRequest) throws TofInternalException {
        clientAuthService.loginClient(clientAuthRequest);
    }

    public void activate(String token) throws TofInternalException {
        clientAuthService.activateUser(token);
    }

    //TODO create reset password service
    public void changePassword(ClientAuthRequest clientAuthRequest) throws TofInternalException {
        clientAuthService.changePassword(clientAuthRequest);
    }

    public void createClientContactData(ClientContactDataRequest contactDataRequest) throws TofInternalException {
        clientDataService.createClientContactData(contactDataRequest);
    }

    public void updateClientContactData(ClientContactDataRequest clientContactDataRequest) throws TofInternalException {
        clientDataService.updateClientContactData(clientContactDataRequest);
    }
}
