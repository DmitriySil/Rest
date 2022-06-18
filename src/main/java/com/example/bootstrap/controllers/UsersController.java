package com.example.bootstrap.controllers;

import com.example.bootstrap.models.User;
import com.example.bootstrap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping( "/")
    public String registration(@ModelAttribute("user") User user) {
        return "registration";
    }
    @GetMapping( "/login")
    public String login() {
        return "login";
    }

//    @PostMapping("/registration")
//    public String register(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "/login";
//    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "user";
    }
}