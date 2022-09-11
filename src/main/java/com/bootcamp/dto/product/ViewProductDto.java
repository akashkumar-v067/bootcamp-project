package com.bootcamp.dto.product;


import com.bootcamp.entities.product.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewProductDto extends Product {
    Long id;
    @Column(nullable = false,unique = true)
    private String productName;

    @Column(nullable = false,unique = true)
    private String brand;

    private Boolean isCancellable;

    private Boolean isReturnable;

    private String description;

    private boolean isActive;

    String name;
    List<String> fieldName;
    List<String> values;

}
