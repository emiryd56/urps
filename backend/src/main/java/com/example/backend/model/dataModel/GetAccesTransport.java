package com.example.backend.model.dataModel;

import com.example.backend.model.passenger.Yolcu;
import com.example.backend.service.DataService;

public class GetAccesTransport {

    private static final double MAX_WALK_DISTANCE_KM = 3.0;
    private Data data;
    private Double taxiOpeningFee;
    private Double taxiCostPerKm;
    private DataService dataService;
    private static Haversine haversine = new Haversine();
    private NearestStop nearestStopClass;

    public GetAccesTransport(DataService dataService) {
        this.dataService = dataService;
        this.data = dataService.getCityData();
        this.taxiCostPerKm = data.taxi.costPerKm;
        this.taxiOpeningFee = data.taxi.openingFee;
        this.nearestStopClass = new NearestStop(data);
    }

    public RouteStep calculate(Location location, Yolcu yolcu) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }

        if (location.getLat() == null || location.getLon() == null) {
            throw new IllegalArgumentException("Location coordinates cannot be null");
        }

        if (location.isStopBased()) {
            Durak stop = dataService.findDurakById(location.getStopId());
            if (stop != null) {
                return new RouteStep(
                        "walk",
                        location,
                        stop,
                        0,
                        0,
                        0
                );
            }
        }

        Durak nearestStop = nearestStopClass.calculate(location);
        if (nearestStop == null) {
            throw new IllegalStateException("No nearest stop found");
        }

        if (nearestStop.getLat() == null || nearestStop.getLon() == null) {
            throw new IllegalStateException("Nearest stop coordinates cannot be null");
        }

        double distance = haversine.calculate(
                location.getLat().doubleValue(),
                location.getLon().doubleValue(),
                nearestStop.getLat().doubleValue(),
                nearestStop.getLon().doubleValue()
        );

        if (distance > MAX_WALK_DISTANCE_KM) {
            double fare = taxiOpeningFee + (distance * taxiCostPerKm);
            double discountedFare = fare ;
            return new RouteStep(
                    "taxi",
                    location,
                    nearestStop,
                    distance,
                    (int) (distance * 2),
                    discountedFare
            );
        } else {
            return new RouteStep(
                    "walk",
                    location,
                    nearestStop,
                    distance,
                    (int) (distance * 10),
                    0
            );
        }
    }

    public RouteStep calculate(Location destination, Durak fromStop, Yolcu yolcu) {
        if (destination == null || fromStop == null) {
            throw new IllegalArgumentException("Destination and fromStop cannot be null");
        }

        if (destination.getLat() == null || destination.getLon() == null) {
            throw new IllegalArgumentException("Destination coordinates cannot be null");
        }

        if (fromStop.getLat() == null || fromStop.getLon() == null) {
            throw new IllegalStateException("From stop coordinates cannot be null");
        }

        double distance = haversine.calculate(
                destination.getLat().doubleValue(),
                destination.getLon().doubleValue(),
                fromStop.getLat().doubleValue(),
                fromStop.getLon().doubleValue()
        );

        if (distance > MAX_WALK_DISTANCE_KM) {
            double fare = taxiOpeningFee + (distance * taxiCostPerKm);
            double discountedFare = fare ;

            return new RouteStep(
                    "taxi",
                    fromStop,
                    destination,
                    distance,
                    (int) (distance * 2),
                    discountedFare
            );
        } else {
            return new RouteStep(
                    "walk",
                    fromStop,
                    destination,
                    distance,
                    (int) (distance * 10),
                    0
            );
        }
    }
}
