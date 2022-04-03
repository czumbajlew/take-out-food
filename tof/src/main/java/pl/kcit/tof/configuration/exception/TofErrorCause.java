package pl.kcit.tof.configuration.exception;

public enum TofErrorCause {
    CLIENT_ACCOUNT_IS_LOCKED("Account is lock"),
    CLIENT_ACCOUNT_NOT_ACTIVATED("Account is not active"),
    CLIENT_ALREADY_REGISTERED("Client already registered"),
    CLIENT_NOT_FOUND("Client with given mail not registered"),
    DATA_IS_NULL("No data was provided"),
    INCORRECT_PASSWORD("Provided password was not correct"),
    PASSWORD_ALREADY_IN_USE("Given password is existing one"),
    TOKEN_EXPIRED("Given token has expired"),
    TOKEN_ALREADY_USED("Given token was already used"),
    TOKEN_NOT_FOUND("Given token was not found");

    private final String cause;

    TofErrorCause(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return this.cause;
    }
}
