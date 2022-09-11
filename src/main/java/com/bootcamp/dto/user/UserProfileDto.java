package com.bootcamp.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive;
}
