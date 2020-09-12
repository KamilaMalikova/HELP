package com.example.helptest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Category implements Serializable {
    @Id
    private int id;

    @Column(unique = true)
    private String category;

    public Category() {
    }

    public Category(long l, String categoryName) {
        this.id = (int) l;
        this.category = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
