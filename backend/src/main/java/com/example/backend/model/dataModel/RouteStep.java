package com.example.backend.model.dataModel;

public class RouteStep {

    private String mode;
    private Location start;
    private Location end;
    private Durak endStop;
    private Durak startStop;
    private double distance;
    private int duration;
    private double fare;

    public RouteStep(String mode, Location start, Location end, double distance, int duration, double fare) {
        this.mode = mode;
        this.start = start;
        this.end = end;
        this.distance = distance;
        this.duration = duration;
        this.fare = fare;
    }

    public RouteStep(String mode, Durak endStop, Durak startStop, double distance, int duration, double fare) {
        this.mode = mode;
        this.endStop = endStop;
        this.startStop = startStop;
        this.distance = distance;
        this.duration = duration;
        this.fare = fare;
    }

    public RouteStep(String mode, Location start, Durak endStop, double distance, int duration, double fare) {
        this.mode = mode;
        this.start = start;
        this.endStop = endStop;
        this.distance = distance;
        this.duration = duration;
        this.fare = fare;
    }

    public RouteStep(String mode, Durak startStop, Location end, double distance, int duration, double fare) {
        this.mode = mode;
        this.startStop = startStop;
        this.end = end;
        this.distance = distance;
        this.duration = duration;
        this.fare = fare;
    }

    public String getMode() {
        return mode;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public double getDistance() {
        return distance;
    }

    public double getFare() {
        return fare;
    }

    public int getDuration() {
        return duration;
    }

    public Durak getEndStop() {
        return endStop;
    }

    public Durak getStartStop() {
        return startStop;
    }
}
