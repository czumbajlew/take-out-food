package pl.kcit.tof;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import pl.kcit.tof.email.TofEmailProperties;
import pl.kcit.tof.internal.jwt.JwtProperties;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(value = "application")
public class TofProperties {

    @NotNull
    private String baseUrl;

    @NotNull
    @NestedConfigurationProperty
    private TofEmailProperties email;

    @NotNull
    @NestedConfigurationProperty
    private JwtProperties jwt;

}
