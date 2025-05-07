package com.example.backend.model.dataModel;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.model.passenger.Yolcu;
import com.example.backend.service.DataService;

public class TaxiOnlyPath {

    private static Haversine haversine = new Haversine();
    private static final double MAX_WALK_DISTANCE_KM = 3.0;
    private Data data;
    private Double taxiOpeningFee;
    private Double taxiCostPerKm;
    private DataService dataService;

    public TaxiOnlyPath(DataService dataService) {
        this.dataService = dataService;
        this.data = dataService.getCityData();
        this.taxiCostPerKm = data.taxi.costPerKm;;
        this.taxiOpeningFee = data.taxi.openingFee;
    }

    public List<RouteStep> find(Location start, Location end, Yolcu yolcu) {
        List<RouteStep> taxiRoute = new ArrayList<>();

        // Başlangıç noktasından hedef noktaya direkt taksi ile git
        double distance = haversine.calculate(start.getLat(), start.getLon(), end.getLat(), end.getLon());
        double fare = taxiOpeningFee + (distance * taxiCostPerKm);
        double discountedFare = fare ;
        taxiRoute.add(new RouteStep(
                "taxi",
                start,
                end,
                distance,
                (int) (distance * 2), // Ortalama taksi süresi (2 dk/km)
                discountedFare
        ));

        return taxiRoute;
    }
}
