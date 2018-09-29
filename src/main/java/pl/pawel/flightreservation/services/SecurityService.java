package pl.pawel.flightreservation.services;

public interface SecurityService {

    boolean login(String username, String password);
}
