package com.example.backend.model.dataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Data {
    @JsonProperty("city")
    public String city;

    @JsonProperty("taxi")
    public TaxiData taxi;

    @JsonProperty("duraklar")
    public List<Durak> duraklar;
}
