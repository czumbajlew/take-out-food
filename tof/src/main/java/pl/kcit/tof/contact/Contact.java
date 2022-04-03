package pl.kcit.tof.contact;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;
    private String phoneNumber;
    private String city;
    private String zipCode;
    private String street;
    private String buildingAddress;

    public Contact(
            String name,
            String phoneNumber,
            String city,
            String zipCode,
            String street,
            String buildingAddress
    ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.buildingAddress = buildingAddress;
    }
}
