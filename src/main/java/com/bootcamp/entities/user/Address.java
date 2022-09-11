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
public class Address {
    @Id
    @Column(name = "ID")
    long id;
    @Column(name = "CITY")
    String city;
    @Column(name = "STATE")
    String state;
    @Column(name = "COUNTRY")
    String country;
    @Column(name = "ADDRESS_LINE")
    String addressLine;
    @Column(name = "ZIP_CODE")
    String zipCode;
    @Column(name = "LABEL")
    String label;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
