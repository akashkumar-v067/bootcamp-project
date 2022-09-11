package com.bootcamp.controllers;

import com.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration
@RestController
@RequestMapping("/app")
public class ForgotPasswordController {
    @Autowired
    UserService userService;

    @PostMapping(path = "/forgotpassword/{email}")
    public String forgotPassword(@PathVariable("email") String email){
        return userService.forgotPassword(email);
    }


    @PutMapping(path="/resetpassword")
    public String resetPassword(@RequestParam String email, @RequestParam String token,
                                @RequestHeader String password, @RequestHeader String confirmPassword){
            userService.resetPassword(email,token,password,confirmPassword);
            return "password updated successfully";

    }


}
