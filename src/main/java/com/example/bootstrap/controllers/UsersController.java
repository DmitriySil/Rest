package com.example.bootstrap.controllers;

import com.example.bootstrap.models.User;
import com.example.bootstrap.services.RoleService;
import com.example.bootstrap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping( "/")
    public String registration(@ModelAttribute("user") User user) {
        return "login";
    }
    @GetMapping( "/login")
    public String login() {
        return "login";
    }
    @GetMapping( "/admin")
    public String admin(Principal principal, Model model) {
        System.out.println("Go to user " + principal.getName());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "adminPanel";
        //return "admin";
    }

    @GetMapping( "/user")
    public String user(Principal principal, Model model) {
        System.out.println("Go to user " + principal.getName());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "user";
    }



//    @GetMapping("/user")
//    public String showUser(Principal principal, Model model) {
//        model.addAttribute("user", userService.findByEmail(principal.getName()));
//        return "user";
//    }
}