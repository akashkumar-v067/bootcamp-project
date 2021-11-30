package com.bootcamp.Entities.Order;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

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



    public Enum getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Enum fromStatus) {
        this.fromStatus = fromStatus;
    }

    public Enum getToStatus() {
        return toStatus;
    }

    public void setToStatus(Enum toStatus) {
        this.toStatus = toStatus;
    }

    public String getTransitionNotesComments() {
        return transitionNotesComments;
    }

    public void setTransitionNotesComments(String transitionNotesComments) {
        this.transitionNotesComments = transitionNotesComments;
    }

}
