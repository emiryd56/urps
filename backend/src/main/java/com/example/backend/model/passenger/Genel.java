package com.example.backend.model.passenger;

import com.example.backend.model.dataModel.Location;
import com.example.backend.model.payment.Payment;

public class Genel extends Yolcu{
    public Genel(String type, Location end, Location start, Payment payment, Boolean specialDay) {
        super(type,end,start,payment, specialDay);
    }
    @Override
    public double indirim() {
        if(this.specialDay){
            return 1.0;
        }

        return this.payment.getIndirimOrani();
    }
}
