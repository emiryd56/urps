package com.example.backend.model.dataModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Durak {

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("type")
    public String type;

    @JsonProperty("lat")
    public Double lat;

    @JsonProperty("lon")
    public Double lon;

    @JsonProperty("sonDurak")
    public Boolean sonDurak;

    @JsonProperty("stops")
    public List<Stop> stops;

    @JsonProperty("transfer")
    public Transfer transfer;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public Boolean getSonDurak() {
        return sonDurak;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public Transfer getTransfer() {
        return transfer;
    }
}
