package pl.kcit.tof.client.dto;

public record ClientAuthRequest(
        String email,
        String password
) {
}
