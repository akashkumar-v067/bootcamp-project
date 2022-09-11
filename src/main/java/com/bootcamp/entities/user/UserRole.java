package com.bootcamp.entities.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "USER_ROLE")
public class UserRole {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Column(name = "USER_ID")
    long userId;
    @Column(name = "ROLE_ID")
    long roleId;
}
