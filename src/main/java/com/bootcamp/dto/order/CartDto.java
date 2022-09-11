package com.bootcamp.dto.order;

import com.bootcamp.entities.product.ProductVariation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {
    int quantity;
    ProductVariation productVariation;

}
