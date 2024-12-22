package com.milestone.blogger.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

/**
 * Global controller advice to add common model attributes (ex. loggedIn)
 * for all controllers/views.
 */
@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addCommonAttributes(Model model, HttpSession session) {
        Boolean loggedIn = session.getAttribute("loggedIn") != null && 
                           (Boolean) session.getAttribute("loggedIn");

        model.addAttribute("loggedIn", loggedIn);

      //   Long userId = (Long) session.getAttribute("userId");
      //   model.addAttribute("userId", userId);
    }
}
