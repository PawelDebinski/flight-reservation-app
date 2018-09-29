package pl.pawel.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public boolean login(String username, String password) {
        LOGGER.info("=== Inside login() -> username: {}, password: {}", username, password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // userDetails.getAuthorities -> przekazuje Role jakie ma user np. 'admin', 'user' itp.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());
        LOGGER.info("=== Createing token: {}", token);
        authenticationManager.authenticate(token);

        boolean result = token.isAuthenticated();
        LOGGER.info("=== Authentication result - token.isAuthenticated(): {}", result);

        // Spring będzie przechowywał informacje o zalogowaniu, żeby nie musiał o nie pytać ciągle, lub wysyłać nam strony z logowaniem ponownie
        if(result) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        return result;
    }
}
