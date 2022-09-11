package com.bootcamp.entities.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    long id;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "TOKEN",unique = true)
    private String token;

    @Column(name = "FORGOT_PASS_TOKEN",unique = true)
    private String forgotPassToken;

    @CreationTimestamp
    @Column(name = "TIME_STAMP",updatable = false)
    private Timestamp timeStamp;

    @Column(name = "EXPIRE_AT",updatable = false)
    @Basic(optional = false)
    private LocalDateTime expireAt;

    private boolean isExpired=false;

}
