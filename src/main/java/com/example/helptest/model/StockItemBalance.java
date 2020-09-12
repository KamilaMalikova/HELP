package com.example.helptest.model;

import javax.persistence.*;

@Entity
public class StockItemBalance {
    @Id
    protected long id;

    protected String name;

    @ManyToOne
    @JoinColumn(name = "unit", referencedColumnName = "id")
    protected Units unit;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    protected Category category;

    private double inStockQty;

    private long productId;

    private boolean restaurant;

    public double getInStockQty() {
        return inStockQty;
    }

    public void setInStockQty(double inStockQty) {
        this.inStockQty = inStockQty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public boolean isRestaurant() {
        return restaurant;
    }

    public void setRestaurant(boolean restaurant) {
        this.restaurant = restaurant;
    }
}
