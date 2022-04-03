package pl.kcit.tof.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kcit.tof.client.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByEmail(String email);

    @Modifying
    @Query(value = "UPDATE registered_client SET active = true, activation_date = NOW() WHERE email = :email;", nativeQuery = true)
    int activateClient(String email);

    @Modifying
    @Query(value = "UPDATE registered_client SET password = :password WHERE email = :email;", nativeQuery = true)
    int changePassword(String password, String email);

}
