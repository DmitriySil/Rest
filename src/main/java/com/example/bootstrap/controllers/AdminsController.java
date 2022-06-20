package com.example.bootstrap.controllers;

import com.example.bootstrap.models.Role;
import com.example.bootstrap.models.User;
import com.example.bootstrap.services.RoleService;
import com.example.bootstrap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminsController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userServiceImp, RoleService roleService) {
        this.userService = userServiceImp;
        this.roleService = roleService;
    }


    @GetMapping("/admin/users")
    public String showUser(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String showUsers(Principal principal, Model model) {
        System.out.println("Go to user " + principal.getName());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "admin";
    }
//    @GetMapping("/admin/user/{id}")
//    public String showUser(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.showUser(id));
//        return "/user";
//    }

    @GetMapping( "/admin/user/add")
    public String addUser(Model model, @ModelAttribute("user") User user,
                          @ModelAttribute("role") Role role) {
        model.addAttribute("roles", roleService.listRoles());
        return "add";
    }

    @PostMapping("/admin/user/add")
    public String add(@ModelAttribute("user") User user,
                      @RequestParam("selectedRole") Long[] roleId) {

        Set<Role> roles = new HashSet<>();
        for (Long id: roleId) {
            roles.add(roleService.showRole(id));
        }
        user.setRoles(roles);

        userService.save(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin/user/{id}")
    public String edit(Model model,@PathVariable("id") Long id, @ModelAttribute("role") Role role) {
        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("roles", roleService.listRoles());
        return "edit";
    }
    @PostMapping("/admin/user/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
    @PostMapping("/admin/user/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("selectedRole") Long[] roleId) {

        Set<Role> roles = new HashSet<>();
        for (Long id: roleId) {
            roles.add(roleService.showRole(id));
        }
        user.setRoles(roles);

        userService.update(user);
        return "redirect:/admin";
    }
}