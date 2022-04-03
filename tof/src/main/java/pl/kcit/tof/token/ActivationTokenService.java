package pl.kcit.tof.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kcit.tof.client.ClientRepository;
import pl.kcit.tof.client.entity.Client;
import pl.kcit.tof.configuration.exception.TofErrorCause;
import pl.kcit.tof.configuration.exception.TofErrorMessage;
import pl.kcit.tof.configuration.exception.TofErrorType;
import pl.kcit.tof.configuration.exception.TofInternalException;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ActivationTokenService {

    private final ActivationTokenRepository activationTokenRepository;
    private final ClientRepository clientRepository;

    public ActivationTokenService(ActivationTokenRepository activationTokenRepository, ClientRepository clientRepository) {
        this.activationTokenRepository = activationTokenRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void activateToken(String token) throws TofInternalException {
        ActivationToken activationToken = activationTokenRepository.findByToken(token)
                .orElseThrow(() -> TofInternalException
                        .builder()
                        .type(TofErrorType.TOKEN_ERROR)
                        .message(TofErrorMessage.ACTIVATION_CLIENT_FAIL)
                        .cause(TofErrorCause.TOKEN_NOT_FOUND)
                        .build()
                );

        checkToken(activationToken);

        Client client = clientRepository.findClientByEmail(activationToken.getEmail())
                .orElseThrow(() -> TofInternalException
                        .builder()
                        .build()
                );

        client.setActive(true);
        client.setActivationDate(OffsetDateTime.now());

        activationToken.setUsed(true);

        try {
            clientRepository.save(client);
            activationTokenRepository.save(activationToken);
        } catch (Exception e) {
            log.error("Activation failed", e);
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.DATABASE_ERROR)
                    .message(TofErrorMessage.DATABASE_ACTION_FAIL)
                    .cause(e.getMessage())
                    .build();
        }
    }

    public String createToken(String email) throws TofInternalException {
        String token = UUID.randomUUID().toString();

        ActivationToken activationToken = new ActivationToken(
                token,
                email
        );

        try {
            activationTokenRepository.save(activationToken);
        } catch (Exception e) {
            log.error("Creating token failed", e);
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.DATABASE_ERROR)
                    .message(TofErrorMessage.DATABASE_ACTION_FAIL)
                    .cause(e.getMessage())
                    .build();
        }

        return token.toString();
    }

    private void checkToken(ActivationToken activationToken) throws TofInternalException{
        if (activationToken.getExpirationDate().isBefore(OffsetDateTime.now())) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.TOKEN_ERROR)
                    .message(TofErrorMessage.ACTIVATION_CLIENT_FAIL)
                    .cause(TofErrorCause.TOKEN_EXPIRED)
                    .build();
        }

        if (activationToken.isUsed()) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.TOKEN_ERROR)
                    .message(TofErrorMessage.ACTIVATION_CLIENT_FAIL)
                    .cause(TofErrorCause.TOKEN_ALREADY_USED)
                    .build();
        }
    }
}
