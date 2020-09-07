package com.example.helptest.model;

public class TablesDTO {
    private int table_id;
    private boolean reserved;
    private String waiter_name;
    private String username;


    public TablesDTO(EatingPlace table) {
        this.table_id = table.getId();
        this.reserved = table.isReserved();
        this.waiter_name = table.getWaiterName();
        this.username = table.getWaiterUsername();
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getWaiter_name() {
        return waiter_name;
    }

    public void setWaiter_name(String waiter_name) {
        this.waiter_name = waiter_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
