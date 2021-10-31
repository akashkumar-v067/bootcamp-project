package com.bootcamp.Entities.User;

import com.bootcamp.Repository.SellerRepository;
import com.bootcamp.Validation.ValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.JOINED)
@Primary
public class User implements UserDetails,Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "ID")
    UUID id;
    @Column(name = "EMAIL")
    @Email(message = "email should be valid")
    String email;
    @Column(name="PHONE_NUMBER",nullable = false,unique = true)
    @Size(min = 10,max = 10,message = "phone number should be valid 10 digit without STD code")
    String phoneNumber;
    @NotNull(message = "first name should not be null")
    @Column(name = "FIRST_NAME")
    String firstName;
    @Column(name = "MIDDLE_NAME")
    String middleName;
    @NotNull(message = "last name should not be null")
    @Column(name = "LAST_NAME",nullable = false)
    String lastName;
    @Column(name = "PASSWORD")
    String password;
    @Column(name = "IS_DELETED")
    Boolean isDeleted;
    @Column(name = "IS_ACTIVE")
    Boolean isActive;
    @Column(name = "IS_EXPIRED")
    Boolean isExpired;
    @Column(name = "IS_LOCKED")
    Boolean isLocked;
    @Column(name = "INVALID_ATTEMPT_COUNT")
    int invalidAttemptCount;

    @Transient
    @ValidPassword
    private String retypePassword;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private Address address;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "RID"))

    private List<Role> role;


    public User() {
        this.isDeleted = false;
        this.isActive = false;
        this.isExpired = false;
        this.isLocked = false;
        this.invalidAttemptCount = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public int getInvalidAttemptCount() {
        return invalidAttemptCount;
    }

    public void setInvalidAttemptCount(int invalidAttemptCount) {
        this.invalidAttemptCount = invalidAttemptCount;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", isDeleted=" + isDeleted +
                ", isActive=" + isActive +
                ", isExpired=" + isExpired +
                ", isLocked=" + isLocked +
                ", invalidAttemptCount=" + invalidAttemptCount +
                ", retypePassword='" + retypePassword + '\'' +
                ", address=" + address +
                '}';
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
