package com.bootcamp.controllers;

import com.bootcamp.dto.user.AddressDto;
import com.bootcamp.dto.user.SellerDto;
import com.bootcamp.dto.user.SellerProfileDto;
import com.bootcamp.entities.user.Address;
import com.bootcamp.service.CurrentUserService;
import com.bootcamp.service.SellerService;
import com.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@EnableAutoConfiguration
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    SellerService sellerService;
    @Autowired
    UserService userService;
    @Autowired
    CurrentUserService currentUserService;

    @GetMapping("/home")
    ResponseEntity sellerHome(){
        String msg="Seller home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }


    @GetMapping("/profile")
    SellerProfileDto viewProfile(){ return sellerService.viewProfile(); }




    @PatchMapping("/profile/update")
    ResponseEntity updateprofile(@RequestBody SellerDto sellerDto){
        return sellerService.updateProfile(sellerDto);
    }

    @PatchMapping("/password/update")
    String updatePassword(@RequestHeader String Password,@RequestHeader String ConfirmPassword){
        return userService.updatePassword(Password,ConfirmPassword);
    }

    @PutMapping("/address/add")
    String updateAddress(@RequestBody Address address){
        sellerService.addAddress(address);
        return "Address added successfully";
    }

    @PutMapping("/address/update/{id}")
    String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id){
        userService.updateAddress(id, addressDto);
        return "Address with id "+id+" updated successfully";
    }





















}
