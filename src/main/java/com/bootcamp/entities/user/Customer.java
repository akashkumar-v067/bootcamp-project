package com.bootcamp.entities.user;

import com.bootcamp.entities.order.Cart;
import com.bootcamp.entities.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PrimaryKeyJoinColumn(name="USER_ID",referencedColumnName = "ID")
public class Customer extends User implements Serializable {
    @Column(name = "USER_ID")
    long id;
    @Column(name = "CONTACT")
    String contact;

    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<Order> orders;

}
