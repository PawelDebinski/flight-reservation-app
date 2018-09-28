package pl.pawel.flightreservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pawel.flightreservation.dto.ReservationRequest;
import pl.pawel.flightreservation.entities.Flight;
import pl.pawel.flightreservation.entities.Passenger;
import pl.pawel.flightreservation.entities.Reservation;
import pl.pawel.flightreservation.repos.FlightRepository;
import pl.pawel.flightreservation.repos.PassengerRepository;
import pl.pawel.flightreservation.repos.ReservationRepository;
import pl.pawel.flightreservation.util.EmailUtil;
import pl.pawel.flightreservation.util.PDFGenerator;

import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PDFGenerator pdfGenerator;

    @Autowired
    EmailUtil emailUtil;

    @Override
    public Reservation bookFlight(ReservationRequest request) {
        // there goes payment logic

        Long flightId = request.getFlightId();
        Flight flight = flightRepository.findById(flightId).get();

        Passenger passenger = new Passenger();
        passenger.setFirstName(request.getPassengerFirstName());
        passenger.setLastName(request.getPassengerLastName());
        passenger.setPhone(request.getPassengerPhone());
        passenger.setEmail(request.getPassengerEmail());
        Passenger savedPassenger = passengerRepository.save(passenger);

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(savedPassenger);
        reservation.setCheckedIn(false);
        Reservation savedReservation = reservationRepository.save(reservation);

        String filePath = "C:\\Users\\elbabol\\Downloads\\reservation" + savedReservation.getId() + ".pdf";
        pdfGenerator.generateItinerary(savedReservation, filePath);
        emailUtil.sendItinerary(passenger.getEmail(), filePath);

        return savedReservation;
    }
}
