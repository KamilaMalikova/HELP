package com.example.helptest.model;

import javax.persistence.*;

@Entity
public class EatingPlace {
    @Id
    //@GeneratedValue(strategy = GenerationType.TABLE, generator = "eating_place_sequence")
    private int id;

    private boolean reserved;

    //    @ManyToOne()
//    @JoinColumn(name = "username", referencedColumnName = "username", insertable = true, updatable = true)
//    private User waiter;
    private String waiterUsername;

    private String waiterName;

    private boolean active = true;

    public EatingPlace(int id, User waiter) {
        this.id = id;
        this.reserved = false;
        this.waiterUsername = waiter.getUsername();
        this.waiterName = "free";
        this.active = true;
    }

    public EatingPlace(int id) {
        this.id = id;
        this.reserved = false;
        this.waiterUsername = "null";
        this.waiterName = "free";
        this.active = true;
    }

    public EatingPlace() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterUsername) {
        this.waiterUsername = waiterUsername;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
