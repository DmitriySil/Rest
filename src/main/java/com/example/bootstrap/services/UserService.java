package com.example.bootstrap.services;

import com.example.bootstrap.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
     void save(User user);
     void delete(Long id);
     List<User> listUsers();
     User showUser(long id);
     void update(User user);
     User findByEmail(String name);
}