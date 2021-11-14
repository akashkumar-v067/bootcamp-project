package com.bootcamp.Service;

import com.bootcamp.Dto.UseDto.AddressDto;
import com.bootcamp.Dto.UseDto.CustomerDto;
import com.bootcamp.Dto.UseDto.CustomerProfileDto;
import com.bootcamp.Entities.User.Address;
import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Exceptions.BadRequestException;
import com.bootcamp.Exceptions.NotFoundException;
import com.bootcamp.Exceptions.NullException;
import com.bootcamp.Exceptions.PatternMismatchException;
import com.bootcamp.Repository.CustomerRepository;
import com.bootcamp.Repository.SellerRepository;
import com.bootcamp.Repository.TokenRepository;
import com.bootcamp.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private EmailService emailService;
   // @Autowired
   // private TokenStore tokenStore;
    @Autowired
    private SellerRepository sellerRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    ModelMapper modelMapper;

    public Customer convtToCustomer(CustomerDto customerDto){
        Customer customer=modelMapper.map(customerDto, Customer.class);
        System.out.println("dto to customer object");
        return customer;
    }

    public CustomerDto convtToCustomerDto(Customer customer){
        CustomerDto customerDto=modelMapper.map(customer, CustomerDto.class);
        System.out.printf("customer to dto object");
        return customerDto;
    }

    public CustomerProfileDto toCustomerViewProfileDto(Customer customer){
        CustomerProfileDto customerViewProfileDto = modelMapper.map(customer, CustomerProfileDto.class);
        return customerViewProfileDto;
    }

    public CustomerProfileDto viewProfile() {
        String username=currentUserService.getUser();
        Customer customer= customerRepo.findByEmail(username);
        return toCustomerViewProfileDto(customer);
    }

    public String updateProfile(CustomerDto customer){
        String username=currentUserService.getUser();
        Customer customer1=customerRepo.findByEmail(username);
        if (customer.getFirstName()!=null)
            customer1.setFirstName(customer.getFirstName());
        if (customer.getMiddleName()!=null)
            customer1.setMiddleName(customer.getMiddleName());
        if (customer.getLastName()!=null)
            customer1.setLastName(customer.getLastName());
        if (customer.getContact()!=null)
        {
            if (customer.getContact().toString().matches("(\\+91|0)[0-9]{10}"))
            {
                customer1.setContact(customer.getContact().toString());
            }
            else
            {
                Long[] l = {};
                throw new PatternMismatchException("Phone no should be valid");
            }
        }
        customerRepo.save(customer1);
        return "success";
    }





}
