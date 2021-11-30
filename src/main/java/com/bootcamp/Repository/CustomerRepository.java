package com.bootcamp.Repository;

import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByEmail(String email);

    @Query("select count(*) > 0 from Customer where email=:email")
    public boolean checkEmail(String email);
}
