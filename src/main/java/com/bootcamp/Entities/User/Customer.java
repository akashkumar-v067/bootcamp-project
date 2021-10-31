package com.bootcamp.Entities.User;

import org.hibernate.engine.internal.Cascade;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@PrimaryKeyJoinColumn(name="USER_ID",referencedColumnName = "ID")
public class Customer extends User implements UserDetails, Serializable {

    @Column(name = "USER_ID")
    UUID id;
    @Column(name = "CONTACT")
    String contact;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}
