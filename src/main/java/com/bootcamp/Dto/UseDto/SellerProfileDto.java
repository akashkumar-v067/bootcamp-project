package com.bootcamp.Dto.UseDto;


import javax.validation.constraints.Size;

public class SellerProfileDto extends UserProfileDto {
    //@ValidGST
    @Size(min = 15, max = 15)
    private String GST;

    private String companyName;

    @Size(min = 10, max = 10)
    private Long companyContact;

    private AddressDto addressDto;

    public SellerProfileDto() {
    }

    public SellerProfileDto(Long id, String firstName, String lastName, Boolean isActive, String image, String GST, Long companyContact, String companyName, AddressDto addressDto) {
        super(id, firstName, lastName, isActive, image);
        this.GST=GST;
        this.companyContact=companyContact;
        this.companyName=companyName;
        this.addressDto=addressDto;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(Long companyContact) {
        this.companyContact = companyContact;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }
}
