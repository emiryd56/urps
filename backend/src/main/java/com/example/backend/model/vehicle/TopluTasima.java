package com.example.backend.model.vehicle;

abstract public class TopluTasima extends Vehicle {
    protected Double distance;
    protected Integer duration;
    protected Double fare;

    public TopluTasima(String type, Double distance, Integer duration, Double fare) {
        super(type);
        this.duration = duration;
        this.distance = distance;
        this.fare = fare;
    }

    public Double getDistance() {
        return distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public Double getFare() {
        return fare;
    }
}
