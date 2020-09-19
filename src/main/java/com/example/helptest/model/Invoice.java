package com.example.helptest.model;

import com.example.helptest.invoice.LineGenerator;
import com.example.helptest.repository.TipDao;
import com.example.helptest.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;

public class Invoice {

    private Owner owner;
    private Orders order;
    private double percent;
    private double tip = 0.0;

    public Invoice(Owner owner, Orders order, Tip tip) {
        this.owner = owner;
        this.order = order;
        this.percent = tip.getTip();
    }

    public String getInvoice(){
        String invoice = owner.toString()
                        + LineGenerator.dottedLine()
                        +order.toString()
                        +LineGenerator.dottedLine();
        double subsum = 0.0;
        for (OrderDetail orderDetail: order.getOrderDetails()) {
            invoice+=orderDetail.toString();
            subsum+=orderDetail.getCost();
        }
        if (this.order.getWaiterUsername().equals("null")) tip = 0.0;
        else tip = subsum*percent;

        invoice+=LineGenerator.dottedLine();
        invoice+=LineGenerator.createLine("Всего: ", Double.toString(subsum))+"\n";
        invoice+=LineGenerator.createLine("Чаевые: ", Double.toString(tip))+"\n";
        invoice+=LineGenerator.createLine("Всего к оплате: ", Double.toString(subsum+tip))+"\n\n";
        invoice+=LineGenerator.createLine("Спасибо за ваш выбор!")+"\n".repeat(6);
        return invoice;
    }

    @Override
    public String toString() {
        return getInvoice();
    }

    public Owner getOwner() {
        return owner;
    }

    public Orders getOrder() {
        return order;
    }

    public double getPercent() {
        return percent;
    }

    public double getTip() {
        return tip;
    }
}
