package com.bootcamp.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto extends UserDto {
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 10)
    Long contact;
}
