package com.bootcamp.controllers;

import com.bootcamp.dto.user.AddressDto;
import com.bootcamp.dto.user.CustomerDto;
import com.bootcamp.dto.user.CustomerProfileDto;
import com.bootcamp.service.CustomerService;
import com.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
@EnableAutoConfiguration
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;


    @GetMapping("/home")
    ResponseEntity customerHome(){
        String msg="customer home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @GetMapping("/profile")
    CustomerProfileDto viewProfile(HttpServletRequest request){
        return customerService.viewProfile();
    }

    @PatchMapping("/profile/update")
    ResponseEntity<String> updateProfile(@Valid @RequestBody CustomerDto customerDto){
        return customerService.updateProfile(customerDto);
    }


    @GetMapping("/getAddress")
    List<AddressDto> viewAddress(){
        return userService.getAddress();
    }


    @PostMapping("/address/add")
    String addAddress(@Valid @RequestBody AddressDto addressDto){
        return userService.addAddress(addressDto);
    }


    @DeleteMapping("/address/delete")
    String deleteAddress(@RequestParam("id") Long id){
        return userService.deleteAddress(id);
    }


    @PutMapping("/address/update/{id}")
    String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id){
        userService.updateAddress(id, addressDto);
        return "Address with id "+id+" updated successfully";
    }


    @PatchMapping("/password/update")
    String updatePassword(@RequestHeader String password, @RequestHeader String confirmPassword) {
        return userService.updatePassword(password, confirmPassword);
    }

}
