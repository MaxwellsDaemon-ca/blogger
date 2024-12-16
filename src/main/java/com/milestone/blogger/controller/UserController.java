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

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Show registration page
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // Will render "register.html" from the templates directory
    }

    // Handle user registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Check if username or email already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Username or Email already exists.");
            return "register";
        }

        // Hash the password
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setDatejoined(new Date());

        // Save the user
        userRepository.save(user);
        model.addAttribute("success", "User registered successfully. Please log in.");
        return "login"; // Redirects to the login page upon successful registration
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Will render "login.html" from the templates directory
    }

    // Handle user login
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty() ||
                !passwordEncoder.matches(password, user.get().getPasswordHash())) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }

        // Add loggedIn attribute to session
        session.setAttribute("loggedIn", true);
        session.setAttribute("currentUser", user.get()); // Optional: Store user object in session
        return "redirect:/";
    }

    // Logout user
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Clear all session attributes
        return "redirect:/"; // Redirect to homepage
    }

}
