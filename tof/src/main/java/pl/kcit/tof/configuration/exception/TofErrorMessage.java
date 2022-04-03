package pl.kcit.tof.configuration.exception;

public enum TofErrorMessage {
    ACTIVATION_CLIENT_FAIL("Client with given mail could not be activated"),
    CLIENT_LOGIN_FAIL("Client login fail"),
    CLIENT_REGISTER_FAIL("Client register fail"),
    CLIENT_UPDATE_FAIL("Client update fail"),
    DATA_FAIL("Provided data validation fail"),
    DATABASE_ACTION_FAIL("CRUD operation into DB fail"),
    SENDING_EMAIL_FAILED("Sending email failed");

    private final String message;

    TofErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
