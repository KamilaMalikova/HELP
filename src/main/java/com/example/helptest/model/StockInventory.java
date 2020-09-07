package com.example.helptest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class StockInventory {
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "documentId", referencedColumnName = "documentId", nullable = false)
    @JsonBackReference
    private StockDocument stockDocument;

    private long productId;

    private String productName;

    private double amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StockDocument getStockDocument() {
        return stockDocument;
    }

    public void setStockDocument(StockDocument stockDocument) {
        this.stockDocument = stockDocument;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
