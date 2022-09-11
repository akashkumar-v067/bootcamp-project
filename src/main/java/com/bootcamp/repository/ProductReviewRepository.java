package com.bootcamp.repository;

import com.bootcamp.entities.product.ProductReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {

}
