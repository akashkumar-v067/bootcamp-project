package com.bootcamp.Dto.UseDto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

public class CustomerProfileDto extends UserProfileDto{
    @Size(min = 10, max = 10, message = "invalid phone number")
    private Long contact;

    public CustomerProfileDto() {
    }

    public CustomerProfileDto(@Size(min = 10, max = 10, message = "Contact number invalid") Long contact) {
        this.contact = contact;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }
}
