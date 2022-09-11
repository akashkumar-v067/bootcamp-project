package com.bootcamp.repository;


import com.bootcamp.entities.user.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface AddressRepository extends CrudRepository<Address, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete address from address where id= :id", nativeQuery = true)
    void deleteAddressById(@Param("id") Long id);


}
