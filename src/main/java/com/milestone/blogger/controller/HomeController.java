package com.milestone.blogger.controller;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.milestone.blogger.repository.PostRepository;

/**
 * Controller to handle requests for the homepage.
 */
@Controller
public class HomeController {

    @Autowired
    private PostRepository postRepository;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Handles GET requests for the root URL and renders the homepage.
     *
     * @param model   the {@link Model} to pass attributes to the view.
     * @param session the {@link HttpSession} to check login status.
     * @return the name of the Thymeleaf template for the homepage.
     */
    @GetMapping("/")
    public String showHomepage(Model model, HttpSession session) {
        logger.info("showHomepage(): Accessing homepage");
        model.addAttribute("posts", postRepository.findAll());

        return "homepage";
    }
}
