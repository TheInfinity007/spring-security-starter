package com.github.theinfinity007.spring_boot_security.controller;

import com.github.theinfinity007.spring_boot_security.entity.User;
import com.github.theinfinity007.spring_boot_security.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    UserService userService;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserService userService){
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("In CustomAuthenticationSuccessHandler");

        String username = authentication.getName();
        System.out.println("username = " + username);

        User user = userService.findByUserName(username);

        // Place the user in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // forward to home page
        response.sendRedirect(request.getContextPath() + "/");
    }
}
