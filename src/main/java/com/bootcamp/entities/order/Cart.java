package com.bootcamp.entities.order;


import com.bootcamp.entities.product.ProductVariation;
import com.bootcamp.entities.user.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    long id;
    @Column(name = "QUANTITY")
    int quantity;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "IS_WISHLIST_ITEM")
    boolean isWishlistItem;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

}
