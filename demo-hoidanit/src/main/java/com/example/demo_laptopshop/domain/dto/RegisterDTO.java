package com.example.demo_laptopshop.domain.dto;

import com.example.demo_laptopshop.service.validator.RegisterChecked;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@RegisterChecked
public class RegisterDTO {
    @Size(min = 3, message = "FirstName phải có tối thiểu 3 ký tự")

    private String firstName;
    private String lastName;
    private String password;

    @Size(min = 3, message = "confirm password phải có tối thiểu 3 ký tự")
    private String confirmPassword;
    @Email(message = "Email không hợp lệ",
            regexp = "^[a-zA-Z0-9_!#$%&*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
