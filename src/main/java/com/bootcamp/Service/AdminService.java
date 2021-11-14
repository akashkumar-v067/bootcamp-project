package com.bootcamp.Service;

import com.bootcamp.Dto.UseDto.AddressDto;
import com.bootcamp.Dto.UseDto.RegisteredCustomerDto;
import com.bootcamp.Dto.UseDto.RegisteredSellerDto;
import com.bootcamp.Entities.User.Address;
import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import org.modelmapper.ModelMapper;
import com.bootcamp.Exceptions.EmailAlreadyActiveException;
import com.bootcamp.Exceptions.NotFoundException;
import com.bootcamp.Repository.CustomerRepository;
import com.bootcamp.Repository.SellerRepository;
import com.bootcamp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<RegisteredCustomerDto> getAllRegisteredCustomers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));

        List<RegisteredCustomerDto> list = new ArrayList<>();
        for (long l : userRepo.findIdOfCustomers(paging))
        {
            Optional<User> user1 = userRepo.findById(l);
            User user = user1.get();
            if (user.getId()==l) {
                RegisteredCustomerDto registeredCustomersDTO = modelMapper.map(user,RegisteredCustomerDto.class);
                list.add(registeredCustomersDTO);
            }
        }

        return list;
    }


    public List<RegisteredSellerDto> getAllRegisteredSellers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<RegisteredSellerDto> list = new ArrayList<>();
        for (long l : userRepo.findIdOfSellers(paging))
        {
            Optional<User> user1 = userRepo.findById(l);
            User user = user1.get();
            if (user.getId()==l)
            {
                RegisteredSellerDto registeredSellersDTO = modelMapper.map(user,RegisteredSellerDto.class);
                AddressDto addressDTO = new AddressDto();
                for (Address address : user.getAddresses())
                {
                    addressDTO = modelMapper.map(address,AddressDto.class);
                }
                registeredSellersDTO.setAddressDTO(addressDTO);
                list.add(registeredSellersDTO);
            }
        }

        return list;
    }




}
