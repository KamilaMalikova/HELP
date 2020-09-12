package com.example.helptest.model;

import com.example.helptest.invoice.LineGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetail {
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    @JsonBackReference
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "productName", referencedColumnName = "productName")
    private Product product;

    private double quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCost(){
        return this.getQuantity()*this.getProduct().getCost();
    }
    @Override
    public String toString() {
        double sum = this.getCost();
        String cost = this.getQuantity()+" x "+this.getProduct().getCost()+"|"+LineGenerator.customString(Double.toString(sum), 9, true);

        String data = LineGenerator.createLine(this.product.getProductName(), cost)+"\n";
        return data;
    }


}
