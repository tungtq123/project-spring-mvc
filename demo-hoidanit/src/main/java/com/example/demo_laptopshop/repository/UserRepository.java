package com.example.demo_laptopshop.repository;

import com.example.demo_laptopshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoidanit);
    List<User> findOneByEmail(String email);
    List<User> findAll();
    User getById(Long id);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
