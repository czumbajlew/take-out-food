package pl.kcit.tof.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.kcit.tof.TofProperties;
import pl.kcit.tof.configuration.exception.TofErrorMessage;
import pl.kcit.tof.configuration.exception.TofErrorType;
import pl.kcit.tof.configuration.exception.TofInternalException;

public abstract class AbstractEmailService {

    protected static final String NEW_LINE = "\n";

    protected final String title;
    protected final TofProperties tofProperties;
    protected final JavaMailSender javaMailSender;

    protected abstract String buildEmailText(String message);

    protected AbstractEmailService(
            String title,
            TofProperties tofProperties,
            JavaMailSender javaMailSender
    ) {
        this.title = title;
        this.tofProperties = tofProperties;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String recipient, String message) throws TofInternalException {
        SimpleMailMessage smm = buildEmail(recipient, message);

        try {
            javaMailSender.send(smm);
        } catch (Exception e) {
            throw TofInternalException
                    .builder()
                    .type(TofErrorType.EMAIL_ERROR)
                    .message(TofErrorMessage.SENDING_EMAIL_FAILED)
                    .cause(e.getCause().getMessage())
                    .build();
        }
    }

    protected SimpleMailMessage buildEmail(String recipient, String message) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(tofProperties.getEmail().getUsername());
        smm.setTo(recipient);
        smm.setSubject(title);
        smm.setText(buildEmailText(message));

        return smm;
    }

}
