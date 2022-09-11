package com.bootcamp.service;

import com.bootcamp.dto.user.SellerDto;
import com.bootcamp.dto.user.SellerProfileDto;
import com.bootcamp.entities.user.Address;
import com.bootcamp.entities.user.Seller;
import com.bootcamp.entities.user.User;
import com.bootcamp.exceptions.PatternMismatchException;
import com.bootcamp.repository.CustomerRepository;
import com.bootcamp.repository.SellerRepository;
import com.bootcamp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    SellerRepository sellerRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    ModelMapper modelMapper;



    public Seller convtToSeller(SellerDto sellerDto){
        Seller seller =modelMapper.map(sellerDto, Seller.class);
        return seller;
    }

    public SellerProfileDto toSellerViewProfileDto(Seller seller){
        SellerProfileDto sellerProfileDto=modelMapper.map(seller, SellerProfileDto.class);
        return sellerProfileDto;
    }

    public SellerProfileDto viewProfile(){
        String username=currentUserService.getUser();
        Seller seller= sellerRepo.findByEmail(username);
        return toSellerViewProfileDto(seller);
    }

    public ResponseEntity updateProfile(SellerDto sellerDto){
        String username=currentUserService.getUser();
        Seller seller=sellerRepo.findByEmail(username);
        if (sellerDto.getFirstName()!=null)
            seller.setFirstName(sellerDto.getFirstName());
        if (sellerDto.getMiddleName()!=null)
            seller.setMiddleName(sellerDto.getMiddleName());
        if (sellerDto.getLastName()!=null)
            seller.setLastName(sellerDto.getLastName());
        if (sellerDto.getCompanyContact()!=null)
        {
            if (sellerDto.getCompanyContact().toString().matches("(\\+91|0)[0-9]{10}"))
            {
                seller.setCompanyContact(sellerDto.getCompanyContact());
            }
            else
            {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
            }
        }
        if(sellerDto.getCompanyName()!=null)
            seller.setCompanyName(sellerDto.getCompanyName());
        if(sellerDto.getGst()!=null)
            seller.setGst(sellerDto.getGst());
        sellerRepo.save(seller);
        return ResponseEntity.ok().body("profile is updated successfully");
    }



    public String addAddress(Address address){
        String email=currentUserService.getUser();
        User user=userRepo.findByEmail(email).get();
        address.setUser(user);
         //addressRepo.save(address);
         user.addAddress(address);
        userRepo.save(user);
        return "Address saved";
    }




}
