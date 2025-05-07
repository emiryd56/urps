package com.example.backend.model.payment;

public abstract class Payment {
    protected String paymentType;
    protected double walletBalance;

    public Payment(String paymentType, double walletBalance) {
        this.paymentType = paymentType;
        this.walletBalance = walletBalance;
    }

    public String getPaymentType() {
        return paymentType;
    }
    public abstract Double getIndirimOrani();
    public double getWalletBalance() {
        return walletBalance;
    }
}
