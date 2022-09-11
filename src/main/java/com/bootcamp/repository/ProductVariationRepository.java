package com.bootcamp.repository;


import com.bootcamp.entities.product.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation, Long> {

   public ProductVariation findByProductId(long ProductId);










    @Query(value = "select product_id from product_variation where id =:id",nativeQuery = true)
    public Long getProductId(@Param(value = "id") Long id);

    @Query(value = "select name from product where id in (select product_id from product_variation where quantity_available=:0) and seller_user_id=:id;);", nativeQuery = true)
    public List<Object[]> outOfStockProducts(@Param(value = "id") Long id);

    @Query(value = "select price from product_variation where id =:id",nativeQuery = true)
    public Long getPrice(@Param(value = "id") Long id);

}

