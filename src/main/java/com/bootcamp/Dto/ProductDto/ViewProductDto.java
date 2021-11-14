package com.bootcamp.Dto.ProductDto;


import com.bootcamp.Entities.Product.Product;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.List;


public class ViewProductDto extends Product {
    Long id;
    @Column(nullable = false,unique = true)
    private String productName;

    @Column(nullable = false,unique = true)
    private String brand;

    private Boolean isCancellable;

    private Boolean isReturnable;

    private String description;

    private boolean isActive;

    String name;
    List<String> fieldName;
    List<String> values;

    public ViewProductDto() {
    }

    public ViewProductDto(String productName, String brand, Boolean isCancellable, Boolean isReturnable, String description, boolean isActive) {
        //super(productName, brand, isCancellable, isReturnable, description, isActive);
        this.productName = productName;
        this.brand = brand;
        this.isCancellable = isCancellable;
        this.isReturnable = isReturnable;
        this.description = description;
        this.isActive = isActive;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean getReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFieldName() {
        return fieldName;
    }

    public void setFieldName(List<String> fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

}
