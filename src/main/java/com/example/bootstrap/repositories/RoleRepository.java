package com.example.bootstrap.repositories;

import com.example.bootstrap.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}