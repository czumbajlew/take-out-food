package pl.kcit.tof.configuration.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.kcit.tof.internal.response.TofResponse;
import pl.kcit.tof.internal.response.error.TofErrorResponse;

@Slf4j
@ControllerAdvice
public class TofErrorHandling implements ErrorController {

    @ExceptionHandler(TofInternalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TofResponse<?> tofResponse(TofInternalException exception) {
        return TofResponse
                .builder()
                .error(new TofErrorResponse(exception))
                .build();
    }

}
