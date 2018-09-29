package pl.pawel.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pawel.flightreservation.entities.User;
import pl.pawel.flightreservation.repos.UserRepository;
import pl.pawel.flightreservation.services.SecurityService;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SecurityService securityService;

    @RequestMapping("/showReg")
    public String showRegistrationPage() {
        LOGGER.info(" ===Inside showRegistrationPage()");
        return "login/registerUser";
    }

    @PostMapping("/registerUser")
    public String register(@ModelAttribute("user") User user) {
        LOGGER.info(" ===Inside register()" + user);
        user.setPassword(encoder.encode(user.getPassword())); // encoding password
        userRepository.save(user);
        return "login/login";
    }

    @RequestMapping("/showLogin")
    public String showLoginPage() {
        LOGGER.info(" ===Inside showLoginPage()");
        return "login/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap modelMap) {
        LOGGER.info(" ===Inside login(), and the email is: " + email);
        boolean loginResponse = securityService.login(email, password);

        if(loginResponse)) {
            return "findFlights";
        } else {
            modelMap.addAttribute("msg", "Invalid user name or password. Please try again.");
        }

        return "login/login";
    }
}
