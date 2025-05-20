package com.example.demo_laptopshop.service;

import com.example.demo_laptopshop.domain.Role;
import com.example.demo_laptopshop.domain.User;
import com.example.demo_laptopshop.domain.dto.RegisterDTO;
import com.example.demo_laptopshop.repository.RoleRepository;
import com.example.demo_laptopshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers(){

        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email){
        return this.userRepository.findOneByEmail(email);
    }

    public User getUserById(Long id){
        User user = this.userRepository.getById(id);
        return user;
    }

    public void deleteUserById(Long id){
        this.userRepository.deleteById(id);
    }

    public User handleSaveUser(User user){
        return this.userRepository.save(user);
    }

    public Role getRoleByName(String name){
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO){
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email){
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

}
