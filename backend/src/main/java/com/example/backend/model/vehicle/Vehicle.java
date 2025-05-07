package com.example.backend.model.vehicle;

abstract public class Vehicle {

    protected String type;

    public Vehicle(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
