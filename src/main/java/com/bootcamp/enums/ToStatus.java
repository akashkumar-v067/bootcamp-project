package com.bootcamp.enums;

public enum ToStatus {
    ORDER_PLACED("ORDER_PLACED"),
    CANCELLED("CANCELLED"),
    ORDER_REJECTED("ORDER_REJECTED"),
    ORDER_CONFIRMED("ORDER_CONFIRMED"),
    ORDER_SHIPPED("ORDER_SHIPPED"),
    DELIVERED("DELIVERED"),
    RETURN_REQUESTED("RETURN_REQUESTED"),
    RETURN_REJECTED("RETURN_REJECTED"),
    RETURN_APPROVED("RETURN_APPROVED"),
    PICK_UP_INITIATED("PICK_UP_INITIATED"),
    PICK_UP_COMPLETED("PICK_UP_COMPLETED"),
    REFUND_INITIATED("REFUND_INITIATED"),
    CLOSED("CLOSED"),
    REFUND_COMPLETED("REFUND_COMPLETED");

    private String val;
    private ToStatus(String val){this.val=val;}

    public String getVal(){return val;}

}
