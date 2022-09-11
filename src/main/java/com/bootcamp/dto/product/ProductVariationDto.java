package com.bootcamp.dto.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariationDto extends ProductDto{
    List<String> fields;
    List<String> values;
    Double price;
    Boolean currentActiveStatus;
    Integer QuantityAvailable;

}
