package com.example.bootstrap.services;

import com.example.bootstrap.models.Role;
import com.example.bootstrap.models.User;
import com.example.bootstrap.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public void save(User user, Long[] roleId) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getEmail().equals("admin@mail.com")) {
            user.getRoles().add(new Role("ADMIN"));
        }

        Set<Role> roles = new HashSet<>();
        for (Long id: roleId) {
            System.out.println(id + " IDDDD");
            roles.add(roleService.showRole(id));
        }
         user.setRoles(roles);
        userRepository.save(user);

    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    public List<User> listUsers() {
        return userRepository.findAll();
    }
    public User showUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void update(User user, Long[] roleId) {
        Set<Role> roles = new HashSet<>();
        for (Long id: roleId) {
            roles.add(roleService.showRole(id));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        //System.out.println(user.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getMyAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> getMyAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }
}