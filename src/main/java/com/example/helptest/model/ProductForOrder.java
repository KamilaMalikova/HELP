package com.example.helptest.model;

public class ProductForOrder extends Product{

    public ProductForOrder(long id, String productName, Category category, Units unit) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.unit = unit;
    }
}
