package pl.kcit.tof.internal.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class JwtProperties {

    @NotNull
    private String secretKey;
    @NotNull
    private String tokenPrefix;
    @NotNull
    private long tokenExpiration;

}
