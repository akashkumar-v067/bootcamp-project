package com.bootcamp.Dto.ProductDto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;


public class ProductVariationDto extends ProductDto{
    List<String> fields;
    List<String> values;
    Double price;
    Boolean currentActiveStatus;
    Integer QuantityAvailable;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getCurrentActiveStatus() {
        return currentActiveStatus;
    }

    public void setCurrentActiveStatus(Boolean currentActiveStatus) {
        this.currentActiveStatus = currentActiveStatus;
    }

    public Integer getQuantityAvailable() {
        return QuantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        QuantityAvailable = quantityAvailable;
    }
}
