package com.bootcamp.controllers;

import com.bootcamp.dto.user.RegisteredCustomerDto;
import com.bootcamp.dto.user.RegisteredSellerDto;
import com.bootcamp.repository.UserRepository;
import com.bootcamp.service.AdminService;
import com.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    //1. User hits the api to obtain all registered seller

    @GetMapping("/listAllSellers")
    public List<RegisteredSellerDto> getAllSellers(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                   @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                   @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return adminService.getAllRegisteredSellers(pageNo, pageSize, sortBy);
    }

    //2. User hits the api to obtain all registered customers

    @GetMapping("/listAllCustomers")
    public List<RegisteredCustomerDto> getAllCustomers(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                       @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                       @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return adminService.getAllRegisteredCustomers(pageNo, pageSize, sortBy);
    }



    @PutMapping(path="/activateCustomer/{email}")
    public ResponseEntity activateCustomer(@PathVariable("email") String email){
        return adminService.activateCustomer(email);
    }

    @PutMapping(path="/deactivateCustomer/{email}")
    public ResponseEntity deactivateCustomer(@PathVariable("email") String email){
       return adminService.deactivateCustomer(email);
    }

    @PutMapping(path="/activateSeller/{email}")
    public ResponseEntity activateSeller(@PathVariable("email") String email){
       return adminService.activateSeller(email);
    }

    @PutMapping(path="/deactivateSeller/{email}")
    public ResponseEntity deactivateSeller(@PathVariable("email") String email){
        return adminService.deactivateSeller(email);
    }

}
