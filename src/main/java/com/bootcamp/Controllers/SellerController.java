package com.bootcamp.Controllers;

import com.bootcamp.Dto.UseDto.AddressDto;
import com.bootcamp.Dto.UseDto.SellerDto;
import com.bootcamp.Dto.UseDto.SellerProfileDto;
import com.bootcamp.Entities.User.Address;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Service.CurrentUserService;
import com.bootcamp.Service.SellerService;
import com.bootcamp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

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
