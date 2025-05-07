package com.example.backend.model.payment;

public class KrediKarti extends Payment {
    public KrediKarti(String paymentType, double walletBalance) {
        super(paymentType, walletBalance);
    }

    @Override
    public Double getIndirimOrani() {
        return 0.1;
    }
}
