package com.bootcamp.Entities.Product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String review;
    private String rating;

    private Long customerId;
    private Long productId;



}
