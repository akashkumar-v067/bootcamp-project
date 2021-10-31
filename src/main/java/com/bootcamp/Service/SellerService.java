package com.bootcamp.Service;

import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Repository.CustomerRepository;
import com.bootcamp.Repository.SellerRepository;
import com.bootcamp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    SellerRepository sellerRepo;
    @Autowired
    CustomerRepository customerRepo;



}
