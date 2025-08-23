package com.github.theinfinity007.spring_boot_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(){
//        return "plain-login";
        return "fancy-login";
    }

    @GetMapping("/leaders")
    public String showLeaders(){
        return "leaders";
    }

    @GetMapping("/systems")
    public String showSystems(){
        return "systems";
    }

    // Route for the Customer access denied page, 403
    @GetMapping("/access-denied")
    public String showAccessDenied(){
        return "access-denied";
    }
}
