package pl.kcit.tof.internal.response.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.kcit.tof.configuration.exception.TofErrorCause;
import pl.kcit.tof.configuration.exception.TofErrorMessage;
import pl.kcit.tof.configuration.exception.TofErrorType;
import pl.kcit.tof.configuration.exception.TofInternalException;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TofErrorResponse {

    private TofErrorType type;
    private String message;
    private String cause;

    public TofErrorResponse(
            TofInternalException exception
    ) {
        this.type = exception.getErrorType();
        this.message = exception.getErrorMessage();
        this.cause = exception.getErrorCause();
    }
}
