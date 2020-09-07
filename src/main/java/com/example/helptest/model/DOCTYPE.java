package com.example.helptest.model;

public enum DOCTYPE {
    IN("In"), OUT("Out");

    private String name;
    DOCTYPE(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
