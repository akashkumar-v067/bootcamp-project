package com.bootcamp.dto.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryFilterDto {
    String categoryName;
    List<String> fields;
    List<String> values;
    List<String> brands;
    Double maximumPrice;
    Double minimumPrice;

}
