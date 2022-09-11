package com.bootcamp.dto.user;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerProfileDto extends UserProfileDto {
    //@ValidGST
    @Size(min = 15, max = 15)
    private String GST;

    private String companyName;

    @Size(min = 10, max = 10)
    private Long companyContact;

    private AddressDto addressDto;

}
