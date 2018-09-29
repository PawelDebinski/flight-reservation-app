package pl.pawel.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawel.flightreservation.controllers.ReservationController;
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
@PropertySource("classpath:myconfig.properties")
public class ReservationServiceImpl implements ReservationService {

    @Value("${pl.pawel.flightreservation.itinerary.dirpath}")
    private String ITINERARY_DIR;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Override
    @Transactional
    public Reservation bookFlight(ReservationRequest request) {
        LOGGER.info("=== Inside bookFlight()");
        // there goes payment logic

        Long flightId = request.getFlightId();
        LOGGER.info("=== Flight Id: ()", flightId);
        Flight flight = flightRepository.findById(flightId).get();


        Passenger passenger = new Passenger();
        passenger.setFirstName(request.getPassengerFirstName());
        passenger.setLastName(request.getPassengerLastName());
        passenger.setPhone(request.getPassengerPhone());
        passenger.setEmail(request.getPassengerEmail());
        LOGGER.info("=== Saving passenger: ()", passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(savedPassenger);
        reservation.setCheckedIn(false);
        LOGGER.info("=== Saving reservation: ()", reservation);
        Reservation savedReservation = reservationRepository.save(reservation);

        String filePath = ITINERARY_DIR + savedReservation.getId() + ".pdf";
        LOGGER.info("=== Generating the itinerary");
        pdfGenerator.generateItinerary(savedReservation, filePath);
        LOGGER.info("=== Emailing the itinerary");
        emailUtil.sendItinerary(passenger.getEmail(), filePath);

        return savedReservation;
    }
}
