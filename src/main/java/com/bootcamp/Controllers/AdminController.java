package com.bootcamp.Controllers;

import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Exceptions.EmailAlreadyActiveException;
import com.bootcamp.Exceptions.NotFoundException;
import com.bootcamp.Repository.UserRepository;
import com.bootcamp.Service.AdminService;
import com.bootcamp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;
    @Autowired
    UserRepository userRepo;

//    @GetMapping(path ="/getAllUsers")
//    public Iterable<User> getUser(){
//        return adminService.getUser();
//    }


    @GetMapping(path ="/getAllSellers")
    public Iterable<Seller> getSeller(){
        return adminService.getSeller();
    }

    @GetMapping(path ="/getAllUsers")
    public Iterable<User> getUsers(){
        return adminService.getUser();
    }

    @GetMapping(path="/getAllCustomers")
    public Iterable<Customer> getCustomer(){
        return adminService.getCustomer();
    }

    @PutMapping(path="/activateCustomer/{email}")
    public String activateCustomer(@PathVariable("email") String email){
        return adminService.activateCustomer(email);

    }

    @PutMapping(path="/deactivateCustomer/{email}")
    public String deactivateCustomer(@PathVariable("email") String email){
       return adminService.deactivateCustomer(email);

    }

    @PutMapping(path="/activateSeller/{email}")
    public String activateSeller(@PathVariable("email") String email){
       return adminService.activateSeller(email);

    }

    @PutMapping(path="/deactivateSeller/{email}")
    public String deactivateSeller(@PathVariable("email") String email){
        return adminService.deactivateSeller(email);
    }

}
