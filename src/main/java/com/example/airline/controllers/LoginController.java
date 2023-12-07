package com.example.airline.controllers;

import com.example.airline.model.User;
import com.example.airline.service.FlightManager;
import com.example.airline.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    UserManager userManager;

    @Autowired
    FlightManager flightManager;

    @GetMapping("/user-registration")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/user-registration")
    public String postUserRegistration(@ModelAttribute("user") User user,
                                       Model model) {
        user.setRole(User.UserRole.USER);

        UserManager.RegisterUserResult result = userManager.registerNewAccount(user);

        if(!result.isOK()) {
            model.addAttribute("resultText", result.toString());
            return "invalid-login";
        }

        return "redirect:/menu";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin() {
        return "redirect:/menu";
    }

    @GetMapping("/home")
    public String getHome() {
        return "menu";
    }
}
