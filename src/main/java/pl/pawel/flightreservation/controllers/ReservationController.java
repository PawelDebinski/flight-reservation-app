package pl.pawel.flightreservation.controllers;

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

    @RequestMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap) {
        Flight flight = flightRepository.findById(flightId).get();
        modelMap.addAttribute("flight", flight);
        return "completeReservation";
    }

    @PostMapping("/completeReservation")
    public String completeReservation(ReservationRequest request, ModelMap modelMap) {
        Reservation reservation = reservationService.bookFlight(request);
        modelMap.addAttribute("msg", "Reservation created successfully and id is " + reservation.getId());
        return "reservationConfirmation";
    }
}
