package com.example.backend.model.dataModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stop {
    @JsonProperty("stopId")
    public String stopId;

    @JsonProperty("mesafe")
    public Double mesafe;

    @JsonProperty("sure")
    public Integer sure;

    @JsonProperty("ucret")
    public Double ucret;
}