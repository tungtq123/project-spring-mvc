package com.example.demo_laptopshop.controller.admin;


import com.example.demo_laptopshop.domain.User;
import com.example.demo_laptopshop.service.UploadService;
import com.example.demo_laptopshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          UploadService uploadService,
                          PasswordEncoder passwordEncoder) {
        this.uploadService = uploadService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/admin/user")
    public String getUserPage(Model model){
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("user1", users);
        return "admin/user/show";
    }

    @GetMapping(value = "/admin/user/create")
    public String getCreateUserPage(Model model){
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
         @ModelAttribute("newUser") @Valid User hoidanit,
         BindingResult newUserBindingResult,
         @RequestParam("hoidanitFile") MultipartFile file){

        //validate
        if(newUserBindingResult.hasErrors()){
            return "admin/user/create";
        }

        //
        String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");

        hoidanit.setAvatar(avatar);
        hoidanit.setPassword(hashPassword);
        hoidanit.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));
        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id){
//        model.addAttribute("id", id);
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id){
        User user = this.userService.getUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model,
                             @ModelAttribute("newUser") User user,
                             BindingResult newUserBindingResult,
                             @RequestParam("hoidanitFile") MultipartFile file){

        if(newUserBindingResult.hasErrors()){
            return "admin/user/update";
        }
        User currentUser = this.userService.getUserById(user.getId());
        if(!file.isEmpty()){
            String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
            currentUser.setAvatar(avatar);
        }

        if(currentUser != null){
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());
            currentUser.setAddress(user.getAddress());
            currentUser.setRole(this.userService.getRoleByName(user.getRole().getName()));
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id){
        model.addAttribute("id", id);
        User user = this.userService.getUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User user){
        this.userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }

}


