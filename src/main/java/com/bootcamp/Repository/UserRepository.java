package com.bootcamp.Repository;

import com.bootcamp.Entities.User.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, Long> , PagingAndSortingRepository<User,Long> {

    @Query("select count(*) > 0 from User where email=:email")
    public boolean checkEmail(String email);
    Optional<User> findById(Long id);


     public User findByEmail(String email);


     public UserDetails findByphoneNumber(String phoneNumber);


    @Query(value = "select id from user where id in(select user_id from user_role where role_id in(select rid from role where authority='ROLE_CUSTOMER'))",nativeQuery = true)
    List<Long> findIdOfCustomers(Pageable pageable);


    @Query(value = "select id from user where id in(select user_id from user_role where role_id in(select rid from role where authority='ROLE_SELLER'))",nativeQuery = true)
    List<Long> findIdOfSellers(Pageable pageable);

    @Query(value = "select * from user where is_logged_in=true",nativeQuery = true)
    User findLoggedInUser();


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
