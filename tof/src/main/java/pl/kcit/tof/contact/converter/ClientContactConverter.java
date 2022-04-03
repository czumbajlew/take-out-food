package pl.kcit.tof.contact.converter;

import org.springframework.stereotype.Component;
import pl.kcit.tof.client.ClientContactDataRequest;
import pl.kcit.tof.configuration.exception.TofErrorCause;
import pl.kcit.tof.configuration.exception.TofErrorMessage;
import pl.kcit.tof.configuration.exception.TofErrorType;
import pl.kcit.tof.configuration.exception.TofInternalException;
import pl.kcit.tof.contact.Contact;
import pl.kcit.tof.internal.Converter;

@Component
public class ClientContactConverter implements Converter<ClientContactDataRequest, Contact> {

    @Override
    public Contact convert(ClientContactDataRequest clientContactDataRequest) throws TofInternalException {
        if (clientContactDataRequest == null) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.DATA_ERROR)
                    .message(TofErrorMessage.DATA_FAIL)
                    .cause(TofErrorCause.DATA_IS_NULL)
                    .build();
        }

        return new Contact(
                clientContactDataRequest.name(),
                clientContactDataRequest.phoneNumber(),
                clientContactDataRequest.city(),
                clientContactDataRequest.zipCode(),
                clientContactDataRequest.street(),
                clientContactDataRequest.buildingNumber()
        );
    }

}
