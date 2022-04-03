package pl.kcit.tof.client;

import org.springframework.web.bind.annotation.*;
import pl.kcit.tof.client.dto.ClientAuthRequest;
import pl.kcit.tof.client.serivces.ClientService;
import pl.kcit.tof.configuration.exception.TofErrorHandling;
import pl.kcit.tof.configuration.exception.TofInternalException;
import pl.kcit.tof.internal.TofController;
import pl.kcit.tof.internal.response.TofResponse;

@TofController(path = "/client")
public class ClientController extends TofErrorHandling {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    private TofResponse<?> registerClient(@RequestBody ClientAuthRequest clientAuthRequest) throws TofInternalException {
        clientService.register(clientAuthRequest);

        return TofResponse
                .builder()
                .message("Client registered")
                .build();
    }

    @PostMapping("/login")
    private TofResponse<?> loginClient(@RequestBody ClientAuthRequest clientAuthRequest) throws TofInternalException {
        clientService.login(clientAuthRequest);

        return TofResponse
                .builder()
                .message("Client logged in")
                .build();
    }

    @GetMapping("/activate")
    private TofResponse<?> activateClient(@RequestParam("token") String token) throws TofInternalException {
        clientService.activate(token);

        return TofResponse
                .builder()
                .message("Client account activated")
                .build();
    }

    @PostMapping("/data")
    private TofResponse<?> createClientData(@RequestBody ClientContactDataRequest contactDataRequest) throws TofInternalException {
        clientService.createClientContactData(contactDataRequest);

        return TofResponse
                .builder()
                .message("Client contact data created")
                .build();
    }

    @PutMapping("/data")
    private TofResponse<?> updateClientData(@RequestBody ClientContactDataRequest clientContactDataRequest) throws TofInternalException {
        clientService.updateClientContactData(clientContactDataRequest);

        return TofResponse
                .builder()
                .message("Client contact data updated")
                .build();
    }

}
