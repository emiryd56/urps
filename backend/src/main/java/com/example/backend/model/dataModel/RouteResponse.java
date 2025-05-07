package com.example.backend.model.dataModel;

import java.util.ArrayList;
import java.util.List;

public class RouteResponse {

    private String status;
    private double totalDistance;
    private int totalDuration;
    private double totalFare;
    private List<RouteStep> steps;
    private List<List<RouteStep>> alternativeRoutes;

    public RouteResponse(String status, double totalDistance, int totalDuration, double totalFare, List<RouteStep> steps) {
        this.status = status;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.totalFare = totalFare;
        this.steps = steps;
        this.alternativeRoutes = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public List<RouteStep> getSteps() {
        return steps;
    }

    public List<List<RouteStep>> getAlternativeRoutes() {
        return alternativeRoutes;
    }

    public void setAlternativeRoutes(List<List<RouteStep>> alternativeRoutes) {
        this.alternativeRoutes = alternativeRoutes;
    }
}
