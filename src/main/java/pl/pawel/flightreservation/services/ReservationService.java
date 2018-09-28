package pl.pawel.flightreservation.services;

import pl.pawel.flightreservation.dto.ReservationRequest;
import pl.pawel.flightreservation.entities.Reservation;

public interface ReservationService {

    public Reservation bookFlight(ReservationRequest request);
}
