package com.example.backend.model.payment;

public class Nakit extends Payment{
    public Nakit(String paymentType, double walletBalance) {
        super(paymentType, walletBalance);
    }

    @Override
    public Double getIndirimOrani() {
        return 0.0;
    }
}
