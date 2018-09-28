package pl.pawel.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pawel.flightreservation.entities.User;
import pl.pawel.flightreservation.repos.UserRepository;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

    Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceimpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("=== Inside loadUserByUsername() -> username: {}", username);
        User user = userRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found for email: " + username);
        }

        org.springframework.security.core.userdetails.User userFromSpringSecurity =
                new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRoles());

        return userFromSpringSecurity;
    }
}
