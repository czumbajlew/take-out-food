package pl.kcit.tof.email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class TofEmailProperties {

    @NotNull
    private String host;
    @NotNull
    private int port;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String transportProtocol;
    @NotNull
    private boolean smtpAuth;
    @NotNull
    private boolean smtpStartTlsEnable;
    @NotNull
    private boolean debug;

}
