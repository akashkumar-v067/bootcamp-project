package com.bootcamp.Repository;


import com.bootcamp.Entities.Product.CategoryMetadataField;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoryMetadataFieldRepository extends CrudRepository<CategoryMetadataField,Long>,PagingAndSortingRepository<CategoryMetadataField,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from category_metadata_field_values where category_metadata_id=:category_metadata_id",nativeQuery = true)
    public void remove(@Param("category_metadata_id") Long category_metadata_id);

    @Modifying
    @Transactional
    @Query(value = "delete from category_metadata_field where id=:id",nativeQuery = true)
    public void deleteMetadatField(@Param("id") Long id);

    @Query(value = "select name from category_metadata_field where id = :id",nativeQuery = true)
    public String getNameOfMetadata(@Param("id") Long id);
}
