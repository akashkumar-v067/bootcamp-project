package com.bootcamp.Entities.User;

import org.hibernate.engine.internal.Cascade;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name="USER_ID",referencedColumnName = "ID")
public class Customer extends User implements Serializable {
    @Column(name = "USER_ID")
    long id;
    @Column(name = "CONTACT")
    String contact;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}
