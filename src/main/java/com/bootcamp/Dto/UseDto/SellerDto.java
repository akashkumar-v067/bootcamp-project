package com.bootcamp.Dto.UseDto;


import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SellerDto extends UserDto {
    @Column(unique = true)
    //@Pattern(regexp = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")
    private String gst;

    @Column(unique = true)
    private Long companyContact;

    @Column(unique = true)
    private String companyName;

    public SellerDto() {
    }

    public SellerDto(String email, String username, String firstName, String middleName, String lastName, String password, String confirmPassword, String gst, Long companyContact, String companyName){
        super(email, username, firstName, middleName, lastName, password, confirmPassword);
        this.gst=gst;
        this.companyContact=companyContact;
        this.companyName=companyName;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public Long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(Long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
