package com.bootcamp.Repository;

import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> , CrudRepository<Seller,Long> {
   // @Query(value = "select * from user inner join seller where email=:email",nativeQuery = true)
      public Seller findByEmail(String email);
      @Query(value = "select * from user inner join seller where user.id=seller.user_id and email=:username",nativeQuery = true)
      public Seller findByUsername(String username);

}
