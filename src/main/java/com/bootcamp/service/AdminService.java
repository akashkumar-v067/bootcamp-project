package com.bootcamp.service;

import com.bootcamp.dto.user.RegisteredCustomerDto;
import com.bootcamp.dto.user.RegisteredSellerDto;
import com.bootcamp.entities.user.Customer;
import com.bootcamp.entities.user.Seller;
import org.modelmapper.ModelMapper;
import com.bootcamp.exceptions.EmailAlreadyActiveException;
import com.bootcamp.exceptions.NotFoundException;
import com.bootcamp.repository.CustomerRepository;
import com.bootcamp.repository.SellerRepository;
import com.bootcamp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    SellerRepository sellerRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    ModelMapper modelMapper;


    public ResponseEntity activateCustomer(String email){
        if (customerRepo.checkEmail(email)) {
            Customer customer=customerRepo.findByEmail(email);
            if (customer.getIsActive()) {
                throw new EmailAlreadyActiveException("customer already active");
            }
            else{
                customer.setIsActive(true);
                customerRepo.save(customer);
                return new ResponseEntity("Customer activated",HttpStatus.OK);
            }
        }
        else{
            throw new NotFoundException("enter valid email Customer not found");
        }
    }

    public ResponseEntity deactivateCustomer(String email){
        if (customerRepo.checkEmail(email)) {
            Customer customer=customerRepo.findByEmail(email);
            if (customer.getIsActive()) {
                customer.setIsActive(false);
                customerRepo.save(customer);
                return new ResponseEntity("Customer deactivated",HttpStatus.OK);
            }
            else{
                throw new EmailAlreadyActiveException("customer is not active");
            }
        }
        else{
            throw new NotFoundException("enter valid email Customer not found");
        }

    }

    public ResponseEntity activateSeller(String email){
        if (sellerRepo.checkEmail(email)) {
            Seller seller = sellerRepo.findByEmail(email);
            if (seller.getIsActive()) {
                throw new EmailAlreadyActiveException("seller already active");
            }
            else{
                seller.setIsActive(true);
                sellerRepo.save(seller);
                return new ResponseEntity("Seller activated",HttpStatus.OK);
            }
        }
        else{
            throw new NotFoundException("enter valid email Seller not found");
        }
    }

    public ResponseEntity deactivateSeller(String email){
        if (sellerRepo.checkEmail(email)) {
            Seller seller = sellerRepo.findByEmail(email);
            if (seller.getIsActive()) {
                seller.setIsActive(false);
                sellerRepo.save(seller);
                return new ResponseEntity("Seller deactivated",HttpStatus.OK);
            }
            else{
                throw new EmailAlreadyActiveException("Seller is not active");
            }
        }
        else{
            throw new NotFoundException("enter valid email Seller not found");
        }
    }

    public List<RegisteredCustomerDto> getAllRegisteredCustomers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
          List<Customer> user=customerRepo.findAll(paging).getContent();
          List<RegisteredCustomerDto> list = new ArrayList<>();
          for(Customer l:user){
             RegisteredCustomerDto r= modelMapper.map(l,RegisteredCustomerDto.class);
              list.add(r);
          }

        return list;
    }


    public List<RegisteredSellerDto> getAllRegisteredSellers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<Seller> user=sellerRepo.findAll(paging).getContent();
        List<RegisteredSellerDto> list = new ArrayList<>();
        for(Seller l:user){
            RegisteredSellerDto r= modelMapper.map(l,RegisteredSellerDto.class);
            list.add(r);
        }

        return list;
    }




}
