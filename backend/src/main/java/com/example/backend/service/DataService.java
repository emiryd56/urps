package com.example.backend.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.example.backend.model.dataModel.Data;
import com.example.backend.model.dataModel.Durak;
import com.example.backend.model.dataModel.Stop;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataService {

    private final Data data;

    public DataService() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
        if (inputStream == null) {
            throw new IOException("JSON dosyası bulunamadı.");
        }
        this.data = objectMapper.readValue(inputStream, Data.class);
    }

    public Data getCityData() {
        return data;
    }

    public Durak findDurakById(String stopId) {
        for (Durak durak : data.duraklar) {
            if (durak.id.equals(stopId)) {
                return durak;
            }
        }
        return null;
    }

    public Stop findStopInfo(Durak from, String toId) {
        if (from.stops != null) {
            for (Stop stop : from.stops) {
                if (stop.stopId.equals(toId)) {
                    return stop;
                }
            }
        }
        return null;
    }
}
