package pl.pawel.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawel.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
