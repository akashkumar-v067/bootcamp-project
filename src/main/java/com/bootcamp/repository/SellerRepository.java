package com.bootcamp.repository;

import com.bootcamp.entities.user.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> , CrudRepository<Seller,Long> {
   // @Query(value = "select * from user inner join seller where email=:email",nativeQuery = true)
      public Seller findByEmail(String email);
      @Query(value = "select * from user inner join seller where user.id=seller.user_id and email=:username",nativeQuery = true)
      public Seller findByUsername(String username);

   @Query("select count(*) > 0 from User where email=:email")
   public boolean checkEmail(String email);

}
