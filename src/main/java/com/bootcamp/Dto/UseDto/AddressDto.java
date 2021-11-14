package com.bootcamp.Dto.UseDto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

public class AddressDto {
    private String addressLine;
    private String city;
    private String state;

    //@Size(min = 6, max = 6, message = "Zipcode should be of length 6")
    private Long zipCode;
    private String country;
    private String label;

    public AddressDto() {
    }

    public AddressDto(String addressLine, String city, String state, @Size(min = 6, max = 6, message = "Zipcode should be of length 6") Long zipCode, String country, String label) {
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.label = label;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
