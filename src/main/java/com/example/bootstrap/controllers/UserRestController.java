package com.example.bootstrap.controllers;

import com.example.bootstrap.models.Role;
import com.example.bootstrap.models.User;
import com.example.bootstrap.services.RoleService;
import com.example.bootstrap.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostMapping(value = "/users")
    public ResponseEntity<User> create(@RequestBody @Valid User user) {

        Set<Role> roles = new HashSet<>();
        for (Role name:  user.getRoles()) {
                roles.add(roleService.showRole(Long.parseLong(name.getName())));

        }
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> users() {
        System.out.println("list");
        final List<User> users = userService.listUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") int id) {
        final User user = userService.getUserById(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user) {
        System.out.println("update");

        Set<Role> roles = new HashSet<>();
        for (Role name:  user.getRoles()) {
            roles.add(roleService.showRole(Long.parseLong(name.getName())));

        }
        user.setRoles(roles);
        userService.update(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public String delete(@PathVariable("id") Long id) {

        userService.delete(id);
        return "del";
    }
}
