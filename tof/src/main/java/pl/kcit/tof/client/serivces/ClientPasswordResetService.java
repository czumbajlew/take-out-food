package pl.kcit.tof.client.serivces;

import org.springframework.stereotype.Service;
import pl.kcit.tof.client.ClientRepository;

@Service
public class ClientPasswordResetService {

    private final ClientRepository clientRepository;
    private final ClientAuthService clientAuthService;

    public ClientPasswordResetService(
            ClientRepository clientRepository,
            ClientAuthService clientAuthService
    ) {
        this.clientRepository = clientRepository;
        this.clientAuthService = clientAuthService;
    }



}
