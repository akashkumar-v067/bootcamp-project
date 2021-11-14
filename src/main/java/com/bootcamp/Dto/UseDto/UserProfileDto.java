package com.bootcamp.Dto.UseDto;

import org.springframework.stereotype.Component;


public class UserProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String image;

    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String firstName, String lastName, Boolean isActive, String image) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
