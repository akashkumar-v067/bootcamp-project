package com.bootcamp.Entities.User;

import javax.persistence.*;


@Entity
public class Address {
    @Id
    @Column(name = "ID")
    long id;
    @Column(name = "CITY")
    String city;
    @Column(name = "STATE")
    String state;
    @Column(name = "COUNTRY")
    String country;
    @Column(name = "ADDRESS_LINE")
    String addressLine;
    @Column(name = "ZIP_CODE")
    String zipCode;
    @Column(name = "LABEL")
    String label;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
