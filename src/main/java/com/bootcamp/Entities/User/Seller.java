package com.bootcamp.Entities.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Entity
@PrimaryKeyJoinColumn(name="USER_ID",referencedColumnName = "ID")
public class Seller extends User implements UserDetails, Serializable {
    @Column(name = "USER_ID")
    UUID id;
    @Column(name ="GST")
    String gst;
    @Column(name ="COMPANY_CONTACT")
    int companyContact;
    @Column(name ="COMPANY_NAME")
    String companyName;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public int getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(int companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
