package com.milestone.blogger.controller;

import com.milestone.blogger.model.User;
import com.milestone.blogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ModelAttribute("loggedIn")
    public Boolean addLoggedInAttribute(HttpSession session) {
        return session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        logger.info("Accessed registration page.");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        logger.info("Attempting to register user: {}", user.getUsername());

        boolean usernameExists = userRepository.findByUsername(user.getUsername()).isPresent();
        boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (usernameExists) {
            logger.warn("Registration failed: Username '{}' already exists.", user.getUsername());
            model.addAttribute("usernameError", "Username '" + user.getUsername() + "'' is already taken.");
        }
        if (emailExists) {
            logger.warn("Registration failed: Email '{}' already exists.", user.getEmail());
            model.addAttribute("emailError", "Email '" + user.getEmail() + "'' is already registered.");
        }
        if (usernameExists || emailExists) {
            return "register";
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setDatejoined(new Date());
        userRepository.save(user);
        logger.info("User registered successfully: {}", user.getUsername());
        model.addAttribute("success", "User registered successfully. Please log in.");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        logger.info("Accessed login page.");
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        logger.info("User login attempt: {}", username);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPasswordHash())) {
            logger.warn("Login failed for username: {}", username);
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }

        session.setAttribute("loggedIn", true);
        logger.info("User logged in successfully: {}", username);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        logger.info("User logged out.");
        session.invalidate();
        return "redirect:/";
    }
}
