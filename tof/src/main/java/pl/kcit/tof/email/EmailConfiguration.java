package pl.kcit.tof.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.kcit.tof.TofProperties;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_START_TLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_DEBUG = "mail.debug";

    private final TofProperties tofProperties;

    public EmailConfiguration(TofProperties tofProperties) {
        this.tofProperties = tofProperties;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        TofEmailProperties emailProperties = tofProperties.getEmail();
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(emailProperties.getPassword());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put(MAIL_TRANSPORT_PROTOCOL, emailProperties.getTransportProtocol());
        properties.put(MAIL_SMTP_AUTH, emailProperties.isSmtpAuth());
        properties.put(MAIL_SMTP_START_TLS_ENABLE, emailProperties.isSmtpStartTlsEnable());
        properties.put(MAIL_DEBUG, emailProperties.isDebug());

        return mailSender;
    }
}
