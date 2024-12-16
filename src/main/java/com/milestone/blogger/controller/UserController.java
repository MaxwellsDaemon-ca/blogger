package com.milestone.blogger.controller;

import com.milestone.blogger.model.User;
import com.milestone.blogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

/**
 * Controller to handle user-related operations such as registration, login, and
 * logout.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Displays the registration page.
     *
     * @return the name of the Thymeleaf template for the registration page.
     */
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    /**
     * Handles user registration.
     *
     * @param user  the user details submitted from the registration form.
     * @param model the {@link Model} to pass attributes to the view.
     * @return the name of the Thymeleaf template for the next view.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Username or Email already exists.");
            return "register";
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setDatejoined(new Date());
        userRepository.save(user);
        model.addAttribute("success", "User registered successfully. Please log in.");
        return "login";
    }

    /**
     * Displays the login page.
     *
     * @return the name of the Thymeleaf template for the login page.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Handles user login.
     *
     * @param username the username entered by the user.
     * @param password the password entered by the user.
     * @param session  the {@link HttpSession} to store login status.
     * @param model    the {@link Model} to pass attributes to the view.
     * @return a redirect to the homepage on successful login, or the login page on
     *         failure.
     */
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPasswordHash())) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }

        session.setAttribute("loggedIn", true);
        return "redirect:/";
    }

    /**
     * Logs out the user by invalidating the session.
     *
     * @param session the {@link HttpSession} to invalidate.
     * @return a redirect to the homepage.
     */
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
