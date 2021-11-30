package com.bootcamp.Dto.UseDto;


import com.bootcamp.Validation.ValidEmail;
import com.bootcamp.Validation.ValidPassword;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class UserDto {

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email(message = "invalid_email")
    @ValidEmail
    private String email;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Enter the UserName")
    private String username;


    @Column(nullable = false)
    @NotEmpty(message = "Enter the First Name")
    private String firstName;

    @Column(nullable = true)
    private String middleName;

    @Column(nullable = false)
    @NotEmpty(message = "Enter the Last Name")
    private String lastName;

    @Column(nullable = false)
    @NotEmpty(message = "Password cant be null")
    @Size(min = 8)
    @ValidPassword
    private String password;

    private String confirmPassword;

    public UserDto(){}

    public UserDto(@NotEmpty @javax.validation.constraints.Email(message = "invalid_email") String email, @NotBlank(message = "Enter the UserName") String username, @NotEmpty(message = "Enter the First Name") String firstName, String middleName, @NotEmpty(message = "Enter the Last Name") String lastName, @NotEmpty(message = "Password cant be null") @Size(min = 8) String password, String confirmPassword) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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
}