package com.example.backend.model.dataModel;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.model.passenger.Yolcu;
import com.example.backend.service.DataService;

public class AlternativeRoutes {

    private NearestStop nearestStop;
    private GetAccesTransport getAccessTransport;
    private TaxiOnlyPath taxiOnlyPath;
    private TransportTypePath transportTypePath;
    private DataService dataService;

    public AlternativeRoutes(Data data, DataService dataService) {
        this.nearestStop = new NearestStop(data);
        this.transportTypePath = new TransportTypePath(dataService);
        this.taxiOnlyPath = new TaxiOnlyPath(dataService);
        this.getAccessTransport = new GetAccesTransport(dataService);
        this.dataService = dataService;
    }

    public List<List<RouteStep>> find(Durak start, Durak target, Yolcu yolcu) {
        List<List<RouteStep>> alternatives = new ArrayList<>();
        Location startLocation = yolcu.getStart();
        Location destinationLocation = yolcu.getEnd();

        if (yolcu.getStart().isStopBased() && !yolcu.getEnd().isStopBased()) {
            Durak durak = dataService.findDurakById(yolcu.getStart().getStopId());
            Location loc = new Location(durak.getLon(), durak.getLat());
            List<RouteStep> taxiOnlyRoute = taxiOnlyPath.find(loc, yolcu.getEnd(), yolcu);
            if (taxiOnlyRoute != null) {
                alternatives.add(taxiOnlyRoute);
            }
        }
        if (!yolcu.getStart().isStopBased() && yolcu.getEnd().isStopBased()) {
            Durak durak = dataService.findDurakById(yolcu.getEnd().getStopId());
            Location loc = new Location(durak.getLon(), durak.getLat());
            List<RouteStep> taxiOnlyRoute = taxiOnlyPath.find(yolcu.getStart(), loc, yolcu);
            if (taxiOnlyRoute != null) {
                alternatives.add(taxiOnlyRoute);
            }
        }
        if (!yolcu.getStart().isStopBased() && !yolcu.getEnd().isStopBased()) {
            List<RouteStep> taxiOnlyRoute = taxiOnlyPath.find(yolcu.getStart(), yolcu.getEnd(), yolcu);
            if (taxiOnlyRoute != null) {
                alternatives.add(taxiOnlyRoute);
            }
        }

        // Sadece otobüs rotası
        Durak busStartStop = startLocation.isStopBased()
                ? dataService.findDurakById(startLocation.getStopId())
                : nearestStop.calculateByType(startLocation, "bus");
        Durak busTargetStop = destinationLocation.isStopBased()
                ? dataService.findDurakById(destinationLocation.getStopId())
                : nearestStop.calculateByType(destinationLocation, "bus");

        if (busStartStop != null && busTargetStop != null) {
            List<RouteStep> busOnlyRoute = transportTypePath.find(busStartStop, busTargetStop, yolcu, "bus");
            if (busOnlyRoute != null) {
                List<RouteStep> fullBusRoute = new ArrayList<>();
                if (!startLocation.isStopBased()) {
                    fullBusRoute.add(getAccessTransport.calculate(startLocation, busStartStop, yolcu));
                }
                fullBusRoute.addAll(busOnlyRoute);
                if (!destinationLocation.isStopBased()) {
                    fullBusRoute.add(getAccessTransport.calculate(destinationLocation, busTargetStop, yolcu));
                }
                alternatives.add(fullBusRoute);
            }
        }

        // Sadece tramvay rotası
        Durak tramStartStop = startLocation.isStopBased()
                ? dataService.findDurakById(startLocation.getStopId())
                : nearestStop.calculateByType(startLocation, "tram");
        Durak tramTargetStop = destinationLocation.isStopBased()
                ? dataService.findDurakById(destinationLocation.getStopId())
                : nearestStop.calculateByType(destinationLocation, "tram");

        if (tramStartStop != null && tramTargetStop != null) {
            List<RouteStep> tramOnlyRoute = transportTypePath.find(tramStartStop, tramTargetStop, yolcu, "tram");
            if (tramOnlyRoute != null) {
                List<RouteStep> fullTramRoute = new ArrayList<>();
                if (!startLocation.isStopBased()) {
                    fullTramRoute.add(getAccessTransport.calculate(startLocation, tramStartStop, yolcu));
                }
                fullTramRoute.addAll(tramOnlyRoute);
                if (!destinationLocation.isStopBased()) {
                    fullTramRoute.add(getAccessTransport.calculate(destinationLocation, tramTargetStop, yolcu));
                }
                alternatives.add(fullTramRoute);
            }
        }

        return alternatives;
    }
}
