package com.example.backend.model.vehicle;

public class BireyselTasimaAraci extends Vehicle {
    protected Double costPerKm;
    protected Double openingFee;

    public BireyselTasimaAraci(String type, Double costPerKm, Double openingFee) {
        super(type);
        this.costPerKm = costPerKm;
        this.openingFee = openingFee;
    }
}
