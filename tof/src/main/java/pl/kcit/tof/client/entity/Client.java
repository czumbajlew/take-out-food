package pl.kcit.tof.client.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kcit.tof.contact.Contact;
import pl.kcit.tof.internal.Role;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "registered_client")
public class Client implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    private boolean active = false;
    private boolean locked = false;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;

    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    @Column(name = "activation_date")
    private OffsetDateTime activationDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id", unique = true)
    private Contact contact;

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
        this.creationDate = OffsetDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
