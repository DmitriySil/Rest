package com.example.bootstrap.config;

import com.example.bootstrap.models.Role;
import com.example.bootstrap.models.User;
import com.example.bootstrap.services.RoleService;
import com.example.bootstrap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitData {

    private final UserService userService;
    private final RoleService roleService;


    public InitData(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void load() {
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleService.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("USER");
        roleService.save(roleUser);

        Set<Role> set = new HashSet<>();
        set.add(roleAdmin);
        set.add(roleUser);

        Set<Role> setUser = new HashSet<>();
        setUser.add(roleUser);


        User userAdmin = new User();
        userAdmin.setFirstname("Mike");
        userAdmin.setLastname("Tyson");
        userAdmin.setAge(55);
        userAdmin.setEmail("MTyson@gmail.com");
        userAdmin.setPassword("IronMike");
        userAdmin.setRoles(set);

        userService.save(userAdmin);

        User user = new User();
        user.setFirstname("Donovan");
        user.setLastname("Ruddock");
        user.setAge(58);
        user.setEmail("Ruddock@gmail.com");
        user.setPassword("Razor");
        user.setRoles(setUser);
        userService.save(user);
    }

}