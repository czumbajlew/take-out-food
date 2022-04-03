package pl.kcit.tof.email.restart;

import org.springframework.mail.javamail.JavaMailSender;
import pl.kcit.tof.TofProperties;
import pl.kcit.tof.email.AbstractEmailService;

public class ResetPasswordEmailService extends AbstractEmailService {

    private static final String TITLE = "Reset password";

    public ResetPasswordEmailService(TofProperties tofProperties, JavaMailSender javaMailSender) {
        super(TITLE, tofProperties, javaMailSender);
    }

    @Override
    protected String buildEmailText(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("Welcome!!!");
        sb.append(NEW_LINE);
        sb.append("To reset Your password click on the link below.");
        sb.append(NEW_LINE);
        sb.append(String.format("%s", message));
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("Link is valid for 24 hours!!!");

        return sb.toString();
    }
}
