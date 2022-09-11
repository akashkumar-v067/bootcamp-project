package com.bootcamp.repository;


import com.bootcamp.entities.product.Product;
import com.bootcamp.entities.product.ProductVariation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
    Product findByName(String name);
    List<Product> findAll();
    List<Product> findAll(Pageable pageable);
    List<Product> findByCategoryId(Long id, Pageable pageable);

    List<Product> findAllBySellerId(long SellerId);























    @Query(value = "select * from product where is_active=:'true'", nativeQuery = true)
    Product activeProducts();

    @Query(value = "select *from product where is_active=:'false'", nativeQuery = true)
    Product nonactiveProducts();

    @Query(value = "select category_id from product where id=:id", nativeQuery = true)
    Long getCategoryId(@Param("id") Long productId);

    @Query(value = "select * from product where id=:id", nativeQuery = true)
    Product findOne(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update Product set isActive= true where id =:id")
    public void activateTheProduct(@Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update Product set isActive= false where id =:id")
    public void deactivateTheProduct(@Param(value = "id") Long id);

    @Query(value = "select id from product where seller_user_id=:seller_user_id",nativeQuery = true)
    List<Long> getProductIdOfSeller(@Param("seller_user_id") Long seller_user_id, Pageable pageable);

    @Query(value = "select id from product", nativeQuery = true)
    List<Long> getAllId(Pageable paging);

    @Query(value = "select * from product where category_id=:category_id",nativeQuery = true)
    List<Long> getIdsOfProducts(@Param("category_id")Long categoryId, Pageable pageable);

    @Query(value = "select id from product where category_id=:category_id and brand=:brand",nativeQuery = true)
    List<Long> getIdOfSimilarProduct(@Param("category_id")Long id, @Param("brand") String brand, Pageable paging);

    @Query("select id from Product where name =:name")
    public Long findProduct(@Param(value = "name") String name);

    @Query(value = "select *from product_variation where product_id= :id", nativeQuery = true)
    ProductVariation findProductVariation(@Param(value = "id") Long id);
}
