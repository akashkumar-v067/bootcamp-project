package com.bootcamp.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {
    private String addressLine;
    private String city;
    private String state;

    //@Size(min = 6, max = 6, message = "Zipcode should be of length 6")
    private Long zipCode;
    private String country;
    private String label;

}
