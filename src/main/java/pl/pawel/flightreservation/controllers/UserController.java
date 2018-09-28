package pl.pawel.flightreservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pawel.flightreservation.entities.User;
import pl.pawel.flightreservation.repos.UserRepository;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/showReg")
    public String showRegistrationPage() {
        return "login/registerUser";
    }

    @PostMapping("/registerUser")
    public String register(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "login/login";
    }

    @RequestMapping("/showLogin")
    public String showLoginPage() {
        return "login/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap modelMap) {
        User user = userRepository.findByEmail(email);
        if(user.getPassword().equals(password)) {
            return "findFlights";
        } else {
            modelMap.addAttribute("msg", "Invalid user name or password. Please try again.");
        }
        return "login/login";
    }
}
