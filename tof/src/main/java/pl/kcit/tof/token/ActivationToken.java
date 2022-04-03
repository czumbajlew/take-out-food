package pl.kcit.tof.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class ActivationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, name = "created_at")
    private OffsetDateTime creationDate;

    @Column(nullable = false, name = "expired_at")
    private OffsetDateTime expirationDate;

    @Column(nullable = false)
    private String email;

    @Column(name = "used")
    private boolean used = false;

    public ActivationToken(
            String token,
            String email
    ) {
        this.token = token;
        this.creationDate = OffsetDateTime.now();
        this.expirationDate = creationDate.plusHours(24);
        this.email = email;
    }
}
