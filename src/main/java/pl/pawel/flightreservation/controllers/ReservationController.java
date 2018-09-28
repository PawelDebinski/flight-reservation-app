package pl.pawel.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pawel.flightreservation.dto.ReservationRequest;
import pl.pawel.flightreservation.entities.Flight;
import pl.pawel.flightreservation.entities.Reservation;
import pl.pawel.flightreservation.repos.FlightRepository;
import pl.pawel.flightreservation.services.ReservationService;


@Controller
public class ReservationController {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    ReservationService reservationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);


    @RequestMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap) {
        LOGGER.info(" ===Inside showCompleteReservation() -> flight id: {}", flightId);
        Flight flight = flightRepository.findById(flightId).get();
        modelMap.addAttribute("flight", flight);
        LOGGER.info(" ===Flight is: {}", flight);
        return "completeReservation";
    }

    @PostMapping("/completeReservation")
    public String completeReservation(ReservationRequest request, ModelMap modelMap) {
        LOGGER.info(" ===Inside completeReservation() -> request: {}", request);
        Reservation reservation = reservationService.bookFlight(request);
        modelMap.addAttribute("msg", "Reservation created successfully and id is " + reservation.getId());
        return "reservationConfirmation";
    }
}
