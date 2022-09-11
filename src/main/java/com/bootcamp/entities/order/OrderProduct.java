package com.bootcamp.entities.order;

import com.bootcamp.entities.product.ProductVariation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private String productVariationMetadata;
    @Column(name = "QUANTITY")
    int quantity;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name="ORDER_ID")
    Order orders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = " PRODUCT_VARIATION_ID")
    private ProductVariation productVariation;

   // @JsonIgnore
    @Embedded
    private OrderStatus orderStatus;

}
