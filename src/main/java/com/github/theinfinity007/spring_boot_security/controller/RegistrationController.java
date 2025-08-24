package com.github.theinfinity007.spring_boot_security.controller;

import com.github.theinfinity007.spring_boot_security.entity.User;
import com.github.theinfinity007.spring_boot_security.service.UserService;
import com.github.theinfinity007.spring_boot_security.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

    UserService userService;

    @Autowired
    RegistrationController(UserService userService){
        this.userService = userService;
    }

    // initbinder to convert trim input strings
    // remove the leading and trailing whitespace
    // resolve issue for the validation

    // It will be called for every request coming to the controller
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register/showRegistrationForm")
    public String showRegistrationForm(Model model) {

        WebUser webUser = new WebUser();
        model.addAttribute("webUser", webUser);
        return "register/registration-form";
    }

    @PostMapping("/register/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("webUser") WebUser webUser,
                                          BindingResult theBindingResult,
                                          HttpSession session,
                                          Model model
    ) {

        System.out.println("theBindingResult.hasErrors() = " + theBindingResult.hasErrors());
        if (theBindingResult.hasErrors()) {
            return "register/registration-form";
        }

        String username = webUser.getUsername();
        // Check if the user already exists
        User existingUser = userService.findByUserName(username);
        if(existingUser != null){
            model.addAttribute("webUser", webUser);
            model.addAttribute("registrationError", "Username already exists.");
            return "register/registration-form";
        }

        // Create user and save in db
        userService.save(webUser);

        session.setAttribute("user", webUser);
        return "register/registration-confirmation";
    }
}
