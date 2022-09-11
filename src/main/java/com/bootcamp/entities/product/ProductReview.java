package com.bootcamp.entities.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String review;
    private String rating;
}
