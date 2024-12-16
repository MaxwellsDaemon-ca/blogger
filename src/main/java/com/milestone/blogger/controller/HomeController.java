package com.milestone.blogger.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomepage(Model model, HttpSession session) {
        // Check if the user is logged in
        Boolean loggedIn = session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
        model.addAttribute("loggedIn", loggedIn);

        return "homepage"; 
    }
}
