package com.bootcamp.dto.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class  ProductDto {

    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private Long categoryId;
    private CategoryDto categoryDto;
    private String description;
    private Boolean isReturnable = false;
    private Boolean isCancellable = false;
    private boolean isActive=false;
    private String categoryName;

    List<String> fieldName;
    List<String> values;

}
