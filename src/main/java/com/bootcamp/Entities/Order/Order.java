package com.bootcamp.Entities.Order;

import com.bootcamp.Entities.Enum.Payment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ORDER_TABLE")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    UUID id;
    @Column(name = "CUSTOMER_USER_ID")
    UUID customerUserId;
    @Column(name = "AMOUNT_PAID")
    int amountPaid;
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
    String CUSTOMER_ADDRESS_LABEL;
}
