package com.bootcamp.Dto.ProductDto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;


public class  ProductDto {

    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private Long categoryId;
    private CategoryDto categoryDto;
    private String description;
    private Boolean isReturnable = false;
    private Boolean isCancellable = false;
    private boolean isActive=false;
    private String categoryName;

    List<String> fieldName;
    List<String> values;

    public ProductDto() {
    }

    public ProductDto( @NotNull String name, @NotNull String brand, @NotNull Long categoryId, CategoryDto categoryDto, String description, Boolean isReturnable, Boolean isCancellable, Boolean isActive) {

        this.name = name;
        this.brand = brand;
        this.categoryId = categoryId;
        this.categoryDto = categoryDto;
        this.description = description;
        this.isReturnable = isReturnable;
        this.isCancellable = isCancellable;
        this.isActive=isActive;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getReturnable() {
        return isReturnable;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public Boolean getCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
