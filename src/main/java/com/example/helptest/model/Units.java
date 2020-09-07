package com.example.helptest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Units implements Serializable {
    @Id
    private int id;

    @Column(unique = true)
    private String unitName;

    public Units() {
    }

    public Units(long l, String unit) {
        this.id = (int)l;
        this.unitName = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

}
