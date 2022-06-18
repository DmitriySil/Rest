package com.example.bootstrap.services;

import com.example.bootstrap.models.Role;

import java.util.List;

public interface RoleService {
     void save(Role role);
     List<Role> listRoles();
     Role showRole(long id);

}