package com.bootcamp.service;

import com.bootcamp.dto.user.CustomerDto;
import com.bootcamp.dto.user.CustomerProfileDto;
import com.bootcamp.entities.user.Customer;
import com.bootcamp.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    ModelMapper modelMapper;


    public CustomerProfileDto toCustomerViewProfileDto(Customer customer){
        CustomerProfileDto customerViewProfileDto = modelMapper.map(customer, CustomerProfileDto.class);
        return customerViewProfileDto;
    }

    public CustomerProfileDto viewProfile() {
        String username=currentUserService.getUser();
        Customer customer= customerRepo.findByEmail(username);
        return toCustomerViewProfileDto(customer);
    }

    public ResponseEntity<String> updateProfile(CustomerDto customer){
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
            customer1.setContact(customer.getContact().toString());
        }
        customerRepo.save(customer1);
        return new ResponseEntity<String>("Profile Updated",HttpStatus.OK);
    }





}
