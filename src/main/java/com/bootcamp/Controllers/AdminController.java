package com.bootcamp.Controllers;

import com.bootcamp.Dto.UseDto.RegisteredCustomerDto;
import com.bootcamp.Dto.UseDto.RegisteredSellerDto;
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
import org.springframework.security.core.userdetails.UserDetails;
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
