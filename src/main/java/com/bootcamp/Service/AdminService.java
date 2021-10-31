package com.bootcamp.Service;

import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Exceptions.EmailAlreadyActiveException;
import com.bootcamp.Exceptions.NotFoundException;
import com.bootcamp.Repository.CustomerRepository;
import com.bootcamp.Repository.SellerRepository;
import com.bootcamp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    SellerRepository sellerRepo;
    @Autowired
    CustomerRepository customerRepo;

//    public List<User> getUser(){
//        return  userRepo.findAll();
//
//    }

    public Iterable<Seller> getSeller(){
        return  sellerRepo.findAll();

    }
    public Iterable<Customer> getCustomer(){
        return  customerRepo.findAll();

    }

    public Iterable<User> getUser(){
        return userRepo.findAll();
    }

    public String activateCustomer(String email){
        if (customerRepo.checkEmail(email)) {
            Customer customer = customerRepo.findByEmail(email);
            if (customer.getActive()) {
                throw new EmailAlreadyActiveException("customer already active");
            }
            else{
                userRepo.activateUser(email);
                return "Customer activated Successfully";
            }
        }
        else{
            throw new NotFoundException("enter valid email Customer not found");
        }
    }

    public String deactivateCustomer(String email){
        if (userRepo.checkEmail(email)) {
            User user = userRepo.findByEmail(email);
            if (user.getActive()) {
                userRepo.deactivateUser(email);
                return "Customer deactivated successfully";
            }
            else{
                throw new EmailAlreadyActiveException("customer is not active");
            }
        }
        else{
            throw new NotFoundException("enter valid email Customer not found");
        }

    }

    public String activateSeller(String email){
        if (userRepo.checkEmail(email)) {
            User user = userRepo.findByEmail(email);
            if (user.getActive()) {
                throw new EmailAlreadyActiveException("seller already active");
            }
            else{
                userRepo.activateUser(email);
                return "seller activated successfully";
            }
        }
        else{
            throw new NotFoundException("enter valid email Seller not found");
        }
    }

    public String deactivateSeller(String email){
        if (userRepo.checkEmail(email)) {
            User user = userRepo.findByEmail(email);
            if (user.getActive()) {
                userRepo.deactivateUser(email);
                return "Seller deactivated successfully";
            }
            else{
                throw new EmailAlreadyActiveException("Seller is not active");
            }
        }
        else{
            throw new NotFoundException("enter valid email Seller not found");
        }
    }






}
