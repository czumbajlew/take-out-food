package pl.kcit.tof.client.serivces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kcit.tof.TofProperties;
import pl.kcit.tof.client.ClientRepository;
import pl.kcit.tof.client.dto.ClientAuthRequest;
import pl.kcit.tof.client.entity.Client;
import pl.kcit.tof.configuration.exception.TofErrorCause;
import pl.kcit.tof.configuration.exception.TofErrorMessage;
import pl.kcit.tof.configuration.exception.TofErrorType;
import pl.kcit.tof.configuration.exception.TofInternalException;
import pl.kcit.tof.email.registration.RegistrationEmailService;
import pl.kcit.tof.token.ActivationTokenService;

import java.util.Optional;

@Service
@Slf4j
public class ClientAuthService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RegistrationEmailService registrationEmailService;
    private final ActivationTokenService activationTokenService;
    private final TofProperties tofProperties;

    public ClientAuthService(
            ClientRepository clientRepository,
            BCryptPasswordEncoder passwordEncoder,
            RegistrationEmailService registrationEmailService,
            ActivationTokenService activationTokenService,
            TofProperties tofProperties
    ) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.registrationEmailService = registrationEmailService;
        this.activationTokenService = activationTokenService;
        this.tofProperties = tofProperties;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findClientByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Client with given email was not found")
                );
    }

    @Transactional
    public void registerClient(ClientAuthRequest registeringClient) throws TofInternalException {
        String clientEmail = registeringClient.email();

        isClientAlreadyRegistered(clientEmail);

        Client client = new Client(
                clientEmail,
                passwordEncoder.encode(registeringClient.password())
        );

        try {
            saveClient(client);

            String activationUrl = buildActivationUrl(createClientToken(clientEmail));

            sendActivationEmail(clientEmail, activationUrl);

        } catch (Exception e) {
            log.error("Registered fail", e);
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.DATABASE_ERROR)
                    .message(TofErrorMessage.DATABASE_ACTION_FAIL)
                    .cause(e.getMessage())
                    .build();
        }
    }

    public void loginClient(ClientAuthRequest clientAuthRequest) throws TofInternalException {
        UserDetails userDetails = loadClient(clientAuthRequest.email());

        checkClientStatus(userDetails);

        boolean matches = passwordEncoder.matches(clientAuthRequest.password(), userDetails.getPassword());

        if (!matches) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.CLIENT_ERROR)
                    .message(TofErrorMessage.CLIENT_LOGIN_FAIL)
                    .cause(TofErrorCause.INCORRECT_PASSWORD)
                    .build();
        }
    }

    public void activateUser(String token) throws TofInternalException {
        activationTokenService.activateToken(token);
    }

    public void changePassword(ClientAuthRequest clientAuthRequest) throws TofInternalException{
        UserDetails userDetails = loadClient(clientAuthRequest.email());

        if (passwordEncoder.matches(clientAuthRequest.password(), userDetails.getPassword())) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.CLIENT_ERROR)
                    .message(TofErrorMessage.CLIENT_UPDATE_FAIL)
                    .cause(TofErrorCause.PASSWORD_ALREADY_IN_USE)
                    .build();
        }

        String newPassword = passwordEncoder.encode(clientAuthRequest.password());

        try {
            clientRepository.changePassword(newPassword, clientAuthRequest.email());
        } catch (Exception e) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.DATABASE_ERROR)
                    .message(TofErrorMessage.DATABASE_ACTION_FAIL)
                    .cause(e.getCause().getMessage())
                    .build();
        }
    }

    private UserDetails loadClient(String email) throws TofInternalException{
        try {
            return loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.CLIENT_ERROR)
                    .cause(TofErrorCause.CLIENT_NOT_FOUND)
                    .build();
        }
    }

    private void isClientAlreadyRegistered(String email) throws TofInternalException {
        Optional<Client> clientOptional = clientRepository.findClientByEmail(email);

        if (clientOptional.isPresent()) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.CLIENT_ERROR)
                    .message(TofErrorMessage.CLIENT_REGISTER_FAIL)
                    .cause(TofErrorCause.CLIENT_ALREADY_REGISTERED)
                    .build();
        }
    }

    private void saveClient(Client client) {
        clientRepository.save(client);
        log.info("Client registered");
    }

    private String createClientToken(String email) throws TofInternalException {
        String token = activationTokenService.createToken(email);
        log.info("Activation token created for client");
        log.info("Token: " + token); //TODO remove

        return token;
    }

    private void sendActivationEmail(String email, String activationUrl) throws TofInternalException {
        registrationEmailService.sendEmail(email, activationUrl);
        log.info("Email send to client");
    }

    private String buildActivationUrl(String token) {
        return String.format("%s/client/activate?token=%s",
                tofProperties.getBaseUrl(),
                token
        );
    }

    private void checkClientStatus(UserDetails userDetails) throws TofInternalException {
        if (!userDetails.isEnabled()) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.CLIENT_ERROR)
                    .message(TofErrorMessage.CLIENT_LOGIN_FAIL)
                    .cause(TofErrorCause.CLIENT_ACCOUNT_NOT_ACTIVATED)
                    .build();
        }

        if (!userDetails.isAccountNonLocked()) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.CLIENT_ERROR)
                    .message(TofErrorMessage.CLIENT_LOGIN_FAIL)
                    .cause(TofErrorCause.CLIENT_ACCOUNT_IS_LOCKED)
                    .build();
        }

    }
}
