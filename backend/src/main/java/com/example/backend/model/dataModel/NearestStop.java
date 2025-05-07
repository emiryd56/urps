package com.example.backend.model.dataModel;

import com.example.backend.model.dataModel.Data;
import com.example.backend.model.dataModel.Durak;
import com.example.backend.model.dataModel.Location;

public class NearestStop {
    private Data data;
    private static Haversine haversine = new Haversine() ;

    public NearestStop(Data data) {
        this.data = data;
    }

    public Durak calculate(Location location) {
        Durak nearestStop = null;
        double minDistance = Double.MAX_VALUE;

        for (Durak durak : data.duraklar) {
            double distance = haversine.calculate(location.getLat(), location.getLon(), durak.getLat(), durak.getLon());

            if (distance < minDistance) {
                minDistance = distance;
                nearestStop = durak;
            }
        }
        return nearestStop;
    }
    public Durak calculateByType(Location location, String transportType) {
        Durak nearestStop = null;
        double minDistance = Double.MAX_VALUE;
        for (Durak durak : data.duraklar) {
            if (!durak.type.equals(transportType)) {
                continue;
            }

            double distance = haversine.calculate(location.getLat(), location.getLon(), durak.getLat(), durak.getLon());

            if (distance < minDistance) {
                minDistance = distance;
                nearestStop = durak;
            }
        }
        return nearestStop;
    }
}
