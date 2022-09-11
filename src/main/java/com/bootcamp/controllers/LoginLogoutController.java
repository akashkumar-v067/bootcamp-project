package com.bootcamp.controllers;

import com.bootcamp.service.CurrentUserService;
import com.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@EnableAutoConfiguration
@RestController
public class LoginLogoutController {
    @Autowired
    UserService userService;
    @Autowired
    CurrentUserService currentUserService;

//    @GetMapping("/signIn")
//    public String login(@RequestHeader(name = "username") String username,
//                        @RequestHeader(name = "password") String password) {
//        currentUserService.loginUser(username,password);
//        return "login successful";
//    }



    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        return userService.logout(request);
    }

    @GetMapping("/currentUser")
      public String currentUser(){
        return currentUserService.getUser();
    }


}
