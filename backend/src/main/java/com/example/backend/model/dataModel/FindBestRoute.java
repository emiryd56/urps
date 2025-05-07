package com.example.backend.model.dataModel;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.model.passenger.Yolcu;
import com.example.backend.service.DataService;

public class FindBestRoute {

    private DataService dataService;
    private GetAccesTransport getAccessTransport;
    private NearestStop nearestStop;
    private AlternativeRoutes alternativeRoutesClass;
    private OptimalPath optimalPath;

    public FindBestRoute(DataService dataService) {
        this.dataService = dataService;
        this.getAccessTransport = new GetAccesTransport(dataService);
        this.nearestStop = new NearestStop(dataService.getCityData());
        this.optimalPath = new OptimalPath(dataService);
        this.alternativeRoutesClass = new AlternativeRoutes(dataService.getCityData(), dataService);
    }

    public RouteResponse calculate(Yolcu yolcu) {
        List<RouteStep> steps = new ArrayList<>();
        double totalDistance = 0;
        int totalDuration = 0;
        double totalFare = 0;
        Location startLocation = yolcu.getStart();
        Location destinationLocation = yolcu.getEnd();
        Durak startStop = null;
        if (!startLocation.isStopBased()) {
            RouteStep accessStep = getAccessTransport.calculate(startLocation, yolcu);
            steps.add(accessStep);
            totalDistance += accessStep.getDistance();
            totalDuration += accessStep.getDuration();
            totalFare += accessStep.getFare();
            startStop = accessStep.getEndStop();
        } else {
            startStop = dataService.findDurakById(startLocation.getStopId());
        }
        Durak targetStop = null;
        Location lastLocation = null;
        if (!destinationLocation.isStopBased()) {
            targetStop = nearestStop.calculate(destinationLocation);
            lastLocation = destinationLocation;
        } else {
            targetStop = dataService.findDurakById(destinationLocation.getStopId());
        }
        System.out.println(startStop.getLat());
        System.out.println(targetStop.getLat());
        List<RouteStep> bestRoute = optimalPath.findOptimalPath(startStop, targetStop, yolcu);
        List<List<RouteStep>> alternativeRoutes = alternativeRoutesClass.find(startStop, targetStop, yolcu);

        if (bestRoute != null) {
            steps.addAll(bestRoute);

            // Toplam değerleri hesapla
            for (RouteStep step : bestRoute) {
                totalDistance += step.getDistance();
                totalDuration += step.getDuration();
                totalFare += step.getFare();
            }

            // Eğer son duraktan hedef konuma gidilmesi gerekiyorsa
            if (lastLocation != null) {
                RouteStep finalStep = getAccessTransport.calculate(lastLocation, targetStop, yolcu);
                steps.add(finalStep);
                totalDistance += finalStep.getDistance();
                totalDuration += finalStep.getDuration();
                totalFare += finalStep.getFare();
            }
        }

        // Cüzdan bakiyesi kontrol et
        if (totalFare > yolcu.getPayment().getWalletBalance()) {
            return new RouteResponse(
                    "INSUFFICIENT_FUNDS",
                    totalDistance,
                    totalDuration,
                    totalFare,
                    steps
            );
        }

        RouteResponse response = new RouteResponse(
                "SUCCESS",
                totalDistance,
                totalDuration,
                totalFare,
                steps
        );
        response.setAlternativeRoutes(alternativeRoutes);
        return response;
    }
}
