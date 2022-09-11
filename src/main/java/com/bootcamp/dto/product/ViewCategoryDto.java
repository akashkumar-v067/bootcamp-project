package com.bootcamp.dto.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewCategoryDto {
    String name;
    List<String> fieldName;
    List<String> values;

}
