package com.bootcamp.repository;

import com.bootcamp.entities.order.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends PagingAndSortingRepository<Cart,Long> {

    public Optional<Cart> findByProductVariationId(long productVariationId);
    public List<Cart> findAllByCustomerId(long CustomerId);

    public Optional<Cart> findByProductVariationIdAndCustomerId(long productVariationId,long CustomerId);

    @Transactional
    @Modifying
    @Query(value = "delete from cart where customer_id=:CustomerId",nativeQuery = true)
    public void deleteAllByCustomerId(long CustomerId);

    @Transactional
    @Modifying
    @Query(value = "delete from cart where id=:id",nativeQuery = true)
    public void deleteById(long id);

    @Transactional
    @Modifying
    @Query(value = "delete from cart where product_variation_id=:ProductVariationId",nativeQuery = true)
    public void deleteByProductVariationId(long ProductVariationId);

}
