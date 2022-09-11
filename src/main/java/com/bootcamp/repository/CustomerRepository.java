package com.bootcamp.repository;

import com.bootcamp.entities.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByEmail(String email);

    @Query("select count(*) > 0 from Customer where email=:email")
    public boolean checkEmail(String email);
}
