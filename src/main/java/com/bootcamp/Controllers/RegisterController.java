package com.bootcamp.Controllers;

import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Exceptions.BadRequestException;
import com.bootcamp.Exceptions.TokenNotFoundException;
import com.bootcamp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@EnableAutoConfiguration
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserService userService;

    @RequestMapping(path = "/registration/admin",method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user){
          if(userService.registerAsAdmin(user)){
              return new ResponseEntity<User>(user,HttpStatus.CREATED);
          }
          else
           throw new BadRequestException("error creating User");
    }

    @PostMapping(path = "/registration/seller")
    public ResponseEntity<Seller> registerSeller(@RequestBody Seller seller){
        if(userService.registerAsSeller(seller)){
            return new ResponseEntity<Seller>(seller,HttpStatus.CREATED);
        }
        else
            throw new BadRequestException("error registering seller");
    }

    @PostMapping(path = "/registration/customer")
    public ResponseEntity<Customer> registerUser(@Valid @RequestBody Customer customer){
        if(userService.registerAsCustomer(customer)){
            return new ResponseEntity<Customer>(customer,HttpStatus.CREATED);
        }
        else
            throw new BadRequestException("error registering customer");
    }


    @PutMapping(path="/activation/{email}/")
    public String activateUser(@PathVariable("email") String email,@RequestParam String token){
       if(userService.activateUser(email,token)){
           return "user activated";
       }
       else
           throw new TokenNotFoundException("token should be valid");
    }

    @PostMapping(path="/sendReActivationLink/{email}")
    public void reactivateUser(@PathVariable("email") String email){
        userService.resendActivationLink(email);
    }


}

