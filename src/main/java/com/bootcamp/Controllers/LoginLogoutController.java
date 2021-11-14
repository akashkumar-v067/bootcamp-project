package com.bootcamp.Controllers;

import com.bootcamp.Entities.User.User;
import com.bootcamp.Repository.UserRepository;
import com.bootcamp.Service.CurrentUserService;
import com.bootcamp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
