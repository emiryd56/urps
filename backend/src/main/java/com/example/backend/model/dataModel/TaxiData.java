package com.example.backend.model.dataModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaxiData {
    @JsonProperty("openingFee")
    public Double openingFee;

    @JsonProperty("costPerKm")
    public Double costPerKm;
}