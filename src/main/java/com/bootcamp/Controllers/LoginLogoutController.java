package com.bootcamp.Controllers;

import com.bootcamp.Entities.User.User;
import com.bootcamp.Repository.UserRepository;
import com.bootcamp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@EnableAutoConfiguration
@RestController
public class LoginLogoutController {
    @Autowired
    UserService userService;




    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        return userService.logout(request);
    }


}
