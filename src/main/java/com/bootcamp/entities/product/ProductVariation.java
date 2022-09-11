package com.bootcamp.entities.product;

import com.bootcamp.entities.order.Cart;
import com.bootcamp.entities.order.OrderProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JsonIgnore
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantityAvailable;
    @Positive
    @Column(nullable = false)
    private Double price;
    private String metadata;
    private String infoJson;
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<Cart> carts;

}
