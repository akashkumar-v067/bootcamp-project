package com.bootcamp.Dto.ProductDto;

import org.springframework.stereotype.Component;

import java.util.List;

public class ViewCategoryDto {
    String name;
    List<String> fieldName;
    List<String> values;

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
