package com.example.helptest.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class OrderWrap {
    @JsonSerialize
    private long orderId;
    @JsonSerialize
    private long productId;
    @JsonSerialize
    private double qty;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}
