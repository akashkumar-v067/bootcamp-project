package com.bootcamp.Service;

import com.bootcamp.Dto.UseDto.SellerDto;
import com.bootcamp.Dto.UseDto.SellerProfileDto;
import com.bootcamp.Entities.User.Address;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Exceptions.BadRequestException;
import com.bootcamp.Exceptions.PatternMismatchException;
import com.bootcamp.Exceptions.TokenNotFoundException;
import com.bootcamp.Repository.CustomerRepository;
import com.bootcamp.Repository.SellerRepository;
import com.bootcamp.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        System.out.println("dto to seller object");
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
        User user=userRepo.findByEmail(email);
        address.setUser(user);
         //addressRepo.save(address);
         user.addAddress(address);
        userRepo.save(user);
        return "Address saved";
    }




}
