package com.bootcamp.entities.order;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class OrderStatus {
    private Enum fromStatus;
    private Enum toStatus;
    private String transitionNotesComments;


    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;


}
