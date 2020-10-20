package com.example.helptest.model;

import java.util.ArrayList;
import java.util.List;

public class OrderReport {
    List<OrderDetail> orderDetails;
    double sum = 0.0;
    Tip tip;
    double tip_sum = 0.0;
    double all_sum = 0.0;

    public OrderReport(Tip tip) {
        this.tip = tip;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addOrderDetails(Orders order){
        for (OrderDetail detail: order.getOrderDetails()) {
            addOrderDetail(detail);
        }
    }

    public void addOrderDetail(OrderDetail detail){
        if (this.orderDetails == null) this.orderDetails = new ArrayList<>();
        boolean isNew = true;
        for (OrderDetail orderDetail: orderDetails) {
            if (orderDetail.getProduct().getId() == detail.getProduct().getId()){
                orderDetail.setQuantity(orderDetail.getQuantity()+detail.getQuantity());
                isNew = false;
            }
        }
        if (isNew){
            orderDetails.add(detail);
        }
        setSum(detail.getQuantity()*detail.getProduct().getCost());
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum+=sum;
        this.tip_sum = this.sum*tip.getTip();
        setAll_sum(this.sum, this.tip_sum);
    }

    private void setAll_sum(double sum, double tip_sum) {
        this.all_sum = sum+tip_sum;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public double getTip_sum() {
        return tip_sum;
    }

    public void setTip_sum(double tip_sum) {
        this.tip_sum = tip_sum;
    }

    public double getAll_sum() {
        return all_sum;
    }

    public void setAll_sum(double all_sum) {
        this.all_sum = all_sum;
    }
}
