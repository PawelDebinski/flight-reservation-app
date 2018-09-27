package pl.pawel.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawel.flightreservation.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
