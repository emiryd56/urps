package com.example.backend.model.payment;

public class KentKart extends Payment{
    public KentKart(String paymentType, double walletBalance) {
        super(paymentType, walletBalance);
    }

    @Override
    public Double getIndirimOrani() {
        return 0.3;
    }
}
