package com.bootcamp.Dto.ProductDto;

import org.springframework.stereotype.Component;

import java.util.List;


public class CategoryFilterDto {
    String categoryName;
    List<String> fields;
    List<String> values;
    List<String> brands;
    Double maximumPrice;
    Double minimumPrice;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public Double getMaximumPrice() { return maximumPrice; }

    public void setMaximumPrice(Double maximumPrice) { this.maximumPrice = maximumPrice; }

    public Double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(Double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }
}
