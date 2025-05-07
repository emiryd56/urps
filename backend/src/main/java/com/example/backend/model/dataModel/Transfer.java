package com.example.backend.model.dataModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Transfer {
    @JsonProperty("transferStopId")
    public String transferStopId;

    @JsonProperty("transferSure")
    public Integer sure;

    @JsonProperty("transferUcret")
    public Double ucret;
}