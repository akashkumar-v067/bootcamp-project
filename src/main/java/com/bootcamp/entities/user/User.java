package com.bootcamp.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id;
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
    @Column(name = "IS_LOGGED_IN")
    Boolean isLoggedIn;
    @Column(name="ACCESS_TOKEN")
    String accessToken;
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
   // @ValidPassword
    private String retypePassword;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addresses;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ID"))

    private List<Role> role;

    public void addAddress(Address address){
        if(address!=null){
            if(addresses == null)
                addresses = new HashSet<Address>();
            address.setUser(this);
            addresses.add(address);
        }
    }
}
