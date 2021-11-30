package com.bootcamp.Entities.User;

import com.bootcamp.Entities.Product.Product;
import com.bootcamp.Validation.ValidGST;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;


@Entity
@PrimaryKeyJoinColumn(name="USER_ID",referencedColumnName = "ID")
public class Seller extends User implements Serializable {
    @Column(name = "USER_ID")
    long id;
    //@ValidGST(message = "GST Should be valid")
    @Column(name ="GST")
    String gst;
    @Column(name ="COMPANY_CONTACT")
    long companyContact;
    @Column(name ="COMPANY_NAME")
    String companyName;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
