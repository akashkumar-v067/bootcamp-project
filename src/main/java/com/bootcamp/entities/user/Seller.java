package com.bootcamp.entities.user;

import com.bootcamp.entities.product.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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

}
