package com.github.theinfinity007.spring_boot_security.controller;

import com.github.theinfinity007.spring_boot_security.user.WebUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RegistrationController {

    @GetMapping("/register/showRegistrationForm")
    public String showRegistrationForm(Model model) {

        WebUser webUser = new WebUser();
        model.addAttribute("webUser", webUser);
        return "register/registration-form";
    }
}
