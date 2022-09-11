package com.bootcamp.repository;


import com.bootcamp.entities.product.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues, Long> {

    @Query(value = "select category_metadata_field_id from category_metadata_field_values where category_id=:category_id",nativeQuery = true)
    List<Long> getMetadataId(@Param(value = "category_id") Long id);

    @Query(value = "select field_values from category_metadata_field_values where " +
            "category_id=:category_id and category_metadata_field_id=:category_metadata_field_id",nativeQuery = true)
    String getFieldValuesForCompositeKey(@Param(value = "category_id")Long category_id,
                                         @Param(value = "category_metadata_field_id") Long category_metadata_id);

    @Query(value = "select * from category_metadata_field_values where " +
            "category_id=:category_id and category_metadata_field_id=:category_metadata_field_id ",nativeQuery = true)
    public CategoryMetadataFieldValues getFieldValues(@Param(value = "category_id")Long category_id,
                                                      @Param(value = "category_metadata_field_id") Long category_metadata_id);

}
