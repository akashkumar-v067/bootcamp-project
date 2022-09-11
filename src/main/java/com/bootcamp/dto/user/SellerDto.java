package com.bootcamp.dto.user;


import com.bootcamp.validation.ValidGST;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerDto extends UserDto {
    @Column(unique = true)
    @ValidGST(message = "gst should be valid")
    private String gst;

    @Column(unique = true)
    private Long companyContact;

    @Column(unique = true)
    private String companyName;

}
