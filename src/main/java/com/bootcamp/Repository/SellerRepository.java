package com.bootcamp.Repository;

import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {
   // @Query(value = "select * from user inner join seller where email=:email",nativeQuery = true)
      public Seller findByEmail(String email);


}
