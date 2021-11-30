package com.bootcamp.Repository;

import com.bootcamp.Entities.User.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByauthority(String authority);

}
