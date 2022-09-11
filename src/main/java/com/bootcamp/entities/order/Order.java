package com.bootcamp.entities.order;

import com.bootcamp.enums.Payment;
import com.bootcamp.entities.user.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "ORDER_TABLE")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    long id;
    @Column(name = "AMOUNT_PAID")
    double amountPaid;
    @Column(name = "DATE_CREATED", updatable = false)
    @Basic(optional = false)
    private LocalDateTime dateCreated;
    @Column(name = "PAYMENT_METHOD")
    Enum<Payment>   paymentMethod;
    @Column(name = "CUSTOMER_ADDRESS_CITY")
    String customerCity;
    @Column(name = "CUSTOMER_ADDRESS_STATE")
    String customerState;
    @Column(name = "CUSTOMER_ADDRESS_COUNTRY")
    String customerCountry;
    @Column(name = "CUSTOMER_ADDRESS_ADDRESS_LINE")
    String customerAddressLine;
    @Column(name = "CUSTOMER_ADDRESS_ZIP_CODE")
    String customerZipCode;
    @Column(name = "CUSTOMER_ADDRESS_LABEL")
    String customerAddressLabel;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<OrderProduct> orderProduct;


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
