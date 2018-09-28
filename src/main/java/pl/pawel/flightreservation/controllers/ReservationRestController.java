package pl.pawel.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawel.flightreservation.dto.ReservationUpdateRequest;
import pl.pawel.flightreservation.entities.Reservation;
import pl.pawel.flightreservation.repos.ReservationRepository;

@RestController
public class ReservationRestController {

    @Autowired
    ReservationRepository reservationRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRestController.class);

    @RequestMapping("/reservations/{id}")
    public Reservation findReservation(@PathVariable("id") Long id) {
        LOGGER.info("=== Inside findReservation() -> id: {}", id);
        return reservationRepository.findById(id).get();
    }

    @RequestMapping("/reservations")
    public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
        LOGGER.info("=== Inside updateReservation() -> request: {}", request);
        Reservation reservation = reservationRepository.findById(request.getId()).get();
        reservation.setNumberOfBags(request.getNumberOfBags());
        reservation.setCheckedIn(request.getCheckedIn());
        LOGGER.info("=== Saving reservation");
        return reservationRepository.save(reservation);
    }
}
