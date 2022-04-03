package pl.kcit.tof.email.registration;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.kcit.tof.TofProperties;
import pl.kcit.tof.email.AbstractEmailService;

@Service
public class RegistrationEmailService extends AbstractEmailService {

    private static final String TITLE = "Activation Link";

    public RegistrationEmailService(TofProperties tofProperties, JavaMailSender javaMailSender) {
        super(TITLE, tofProperties, javaMailSender);
    }

    @Override
    protected String buildEmailText(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("Welcome!!!");
        sb.append(NEW_LINE);
        sb.append("To activate Your account please click link below.");
        sb.append(NEW_LINE);
        sb.append(String.format("%s", message));
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("Link is valid for 24 hours!!!");

        return sb.toString();
    }
}
