package pl.kcit.tof.client;

public record ClientContactDataRequest(
        String email,
        String name,
        String phoneNumber,
        String city,
        String zipCode,
        String street,
        String buildingNumber
) {
}
