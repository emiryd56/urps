package com.example.backend.model.dataModel;

public class RouteReq {
    private String userType;
    private String paymentType;
    private double walletBalance;
    private Location start;
    private Location end;
    private Boolean specialDay;


    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public double getWalletBalance() { return walletBalance; }
    public void setWalletBalance(double walletBalance) { this.walletBalance = walletBalance; }

    public Location getStart() { return start; }
    public void setStart(Location start) { this.start = start; }

    public Location getEnd() { return end; }
    public void setEnd(Location end) { this.end = end; }
    public Boolean getSpecialDay(){
        return this.specialDay;
    }
    public void setSpecialDay(Boolean specialDay){
        this.specialDay = specialDay;
    }
}
