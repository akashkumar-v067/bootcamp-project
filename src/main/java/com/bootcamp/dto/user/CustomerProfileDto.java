package com.bootcamp.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerProfileDto extends UserProfileDto{

    @Size(min = 10, max = 10, message = "invalid phone number")
    private Long contact;


}
