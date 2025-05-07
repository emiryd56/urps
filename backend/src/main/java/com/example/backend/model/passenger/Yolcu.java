package com.example.backend.model.passenger;

import com.example.backend.model.dataModel.Location;
import com.example.backend.model.payment.Payment;

abstract public class Yolcu {

    protected String type;
    protected Location end;
    protected Location start;
    protected Payment payment;
    protected Boolean specialDay;


    public Yolcu(String type, Location end, Location start, Payment payment, Boolean specialDay) {
        this.type = type;
        this.end = end;
        this.start = start;
        this.payment = payment;
        this.specialDay = specialDay;
    }

    public abstract double indirim();

    public Location getStart() {
        return start;
    }

    public String getType() {
        return type;
    }

    public Location getEnd() {
        return end;
    }

    public Payment getPayment() {
        return payment;
    }

    public Boolean getSpecialDay() {
        return this.specialDay;
    }
}
