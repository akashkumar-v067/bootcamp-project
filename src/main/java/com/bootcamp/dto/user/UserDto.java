package com.bootcamp.dto.user;


import com.bootcamp.validation.ValidEmail;
import com.bootcamp.validation.ValidPassword;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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

}