package pl.kcit.tof.client.serivces;

import org.springframework.stereotype.Service;
import pl.kcit.tof.client.ClientContactDataRequest;
import pl.kcit.tof.client.ClientRepository;
import pl.kcit.tof.client.entity.Client;
import pl.kcit.tof.configuration.exception.TofErrorCause;
import pl.kcit.tof.configuration.exception.TofErrorMessage;
import pl.kcit.tof.configuration.exception.TofErrorType;
import pl.kcit.tof.configuration.exception.TofInternalException;
import pl.kcit.tof.contact.Contact;
import pl.kcit.tof.contact.converter.ClientContactConverter;

@Service
public class ClientDataService {

    private final ClientRepository clientRepository;
    private final ClientContactConverter clientContactConverter;

    public ClientDataService(ClientRepository clientRepository, ClientContactConverter clientContactConverter) {
        this.clientRepository = clientRepository;
        this.clientContactConverter = clientContactConverter;
    }

    public void createClientContactData(ClientContactDataRequest contactDataRequest) throws TofInternalException {
        Client client = findClient(contactDataRequest.email());

        client.setContact(clientContactConverter.convert(contactDataRequest));

        try {
            clientRepository.save(client);
        } catch (Exception e) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.DATABASE_ERROR)
                    .message(TofErrorMessage.DATABASE_ACTION_FAIL)
                    .cause(e.getCause().getMessage())
                    .build();
        }
    }

    public void updateClientContactData(ClientContactDataRequest clientContactDataRequest) throws TofInternalException {
        Client client = findClient(clientContactDataRequest.email());
        Contact existingClientContactData = client.getContact();
        Contact updatingClientContactData = clientContactConverter.convert(clientContactDataRequest);

        mergeContactData(existingClientContactData, updatingClientContactData);

        client.setContact(updatingClientContactData);

        try {
            clientRepository.save(client);
        } catch (Exception e) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.DATABASE_ERROR)
                    .message(TofErrorMessage.DATABASE_ACTION_FAIL)
                    .cause(e.getCause().getMessage())
                    .build();
        }

    }

    private Client findClient(String email) throws TofInternalException {
        return clientRepository.findClientByEmail(email)
                .orElseThrow(() -> new TofInternalException(
                        TofErrorType.CLIENT_ERROR,
                        TofErrorMessage.CLIENT_UPDATE_FAIL.getMessage(),
                        TofErrorCause.CLIENT_NOT_FOUND.getCause()
                ));
    }

    private void mergeContactData(Contact existing, Contact updating) {
        updating.setName(mergeString(existing.getName(), updating.getName()));
        updating.setPhoneNumber(mergeString(existing.getPhoneNumber(), updating.getPhoneNumber()));
        updating.setCity(mergeString(existing.getCity(), existing.getCity()));
        updating.setZipCode(mergeString(existing.getZipCode(), updating.getZipCode()));
        updating.setStreet(mergeString(existing.getStreet(), updating.getStreet()));
        updating.setBuildingAddress(mergeString(existing.getBuildingAddress(), updating.getBuildingAddress()));
    }

    private String mergeString(String existing, String updating) {
        if (existing.equals(updating)) {
            return existing;
        }

        return updating;
    }
}
