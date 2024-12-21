package com.milestone.blogger.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to handle requests for the homepage.
 */
@Controller
public class HomeController {

    /**
     * Handles GET requests for the root URL and renders the homepage.
     *
     * @param model   the {@link Model} to pass attributes to the view.
     * @param session the {@link HttpSession} to check login status.
     * @return the name of the Thymeleaf template for the homepage.
     */
    @GetMapping("/")
    public String showHomepage(Model model, HttpSession session) {
        Boolean loggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
        model.addAttribute("loggedIn", loggedIn);

        return "homepage";
    }
}