package com.example.backend.model.dataModel;

public class Location {
    private String stopId;
    private Double lat;
    private Double lon;

    public Location(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getStopId() { return stopId; }
    public void setStopId(String stopId) { this.stopId = stopId; }

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLon() { return lon; }
    public void setLon(Double lon) { this.lon = lon; }

    public boolean isStopBased() { return stopId != null && !stopId.isEmpty(); }
    public boolean isLatLonBased() { return lat != null && lon != null; }

}
