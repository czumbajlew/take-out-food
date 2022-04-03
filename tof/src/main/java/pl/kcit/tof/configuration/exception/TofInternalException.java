package pl.kcit.tof.configuration.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TofInternalException extends Exception {

    private final TofErrorType errorType;
    private final String errorMessage;
    private final String errorCause;

    public TofInternalException(TofErrorType errorType, String errorMessage, String errorCause) {
        super(errorMessage);

        this.errorType = errorType;
        this.errorMessage = errorMessage;
        this.errorCause = errorCause;
    }

    public static TofInternalExceptionBuilder builder() {
        return new TofInternalExceptionBuilder();
    }

    @NoArgsConstructor
    public static class TofInternalExceptionBuilder {

        private TofErrorType type;
        private String message;
        private String cause;

        public TofInternalExceptionBuilder type(TofErrorType type) {
            this.type = type;
            return this;
        }

        public TofInternalExceptionBuilder message(TofErrorMessage message) {
            this.message = message.getMessage();
            return this;
        }

        public TofInternalExceptionBuilder cause(TofErrorCause cause) {
            this.cause = cause.getCause();
            return this;
        }

        public TofInternalExceptionBuilder cause(String cause) {
            this.cause = cause;
            return this;
        }

        public TofInternalException build() {
            return new TofInternalException(type, message, cause);
        }
    }
}
