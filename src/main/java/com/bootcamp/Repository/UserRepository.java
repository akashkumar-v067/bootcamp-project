package com.bootcamp.Repository;

import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select count(*) > 0 from User where email=:email")
    public boolean checkEmail(String email);

    @Query("from User where email=:email")
    public User findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value="update user set is_active=true where email=:email",nativeQuery = true)
    public void activateUser(String email);

    @Transactional
    @Modifying
    @Query(value="update user set is_active=false where email=:email",nativeQuery = true)
    public void deactivateUser(String email);

    @Transactional
    @Modifying
    @Query(value="update user set password=:password where email=:email",nativeQuery = true)
    public void resetPassword(String password,String email);

}
