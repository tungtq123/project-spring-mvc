package com.example.demo_laptopshop.repository;

import com.example.demo_laptopshop.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
