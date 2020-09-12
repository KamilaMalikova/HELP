package com.example.helptest.model;

import com.example.helptest.invoice.LineGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Owner {
    @Id
    private int id;
    private String companyName;
    private String INN;
    private String Address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        LocalTime time = LocalDateTime.now().toLocalTime();
        String data = LineGenerator.createLine(this.companyName)+"\n"
                    +LineGenerator.createLine(this.Address)+"\n"
                +LineGenerator.createLine(time.getHour()+":"+((time.getMinute() < 10)? "0"+time.getMinute():time.getMinute()), this.INN)+"\n";

        return data;
    }

}
