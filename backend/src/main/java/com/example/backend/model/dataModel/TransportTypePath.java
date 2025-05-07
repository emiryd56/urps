package com.example.backend.model.dataModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.example.backend.model.passenger.Yolcu;
import com.example.backend.service.DataService;

public class TransportTypePath {

    private Data data;
    private DataService dataService;

    public TransportTypePath(DataService dataService) {
        this.dataService = dataService;
        this.data = dataService.getCityData();
    }

    public List<RouteStep> find(Durak start, Durak target, Yolcu yolcu, String transportType) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Map<String, Double> fares = new HashMap<>();
        Map<String, Integer> durations = new HashMap<>();
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(
                Comparator.comparingDouble(NodeDistance::getDistance));
        Set<String> visited = new HashSet<>();

        // Sadece belirtilen taşıma tipine ait durakları filtrele
        List<Durak> filteredDuraklar = new ArrayList<>();
        for (Durak durak : data.duraklar) {
            if (durak.type.equals(transportType)) {
                filteredDuraklar.add(durak);
            }
        }

        // Başlangıç ve hedef durakları kontrol et
        if (!start.type.equals(transportType) || !target.type.equals(transportType)) {
            return null;
        }

        for (Durak durak : filteredDuraklar) {
            distances.put(durak.id, Double.MAX_VALUE);
            fares.put(durak.id, Double.MAX_VALUE);
            durations.put(durak.id, Integer.MAX_VALUE);
        }

        distances.put(start.id, 0.0);
        fares.put(start.id, 0.0);
        durations.put(start.id, 0);
        queue.add(new NodeDistance(start.id, 0.0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();
            String currentId = current.getNodeId();

            if (currentId.equals(target.id)) {
                break;
            }

            if (visited.contains(currentId)) {
                continue;
            }

            visited.add(currentId);
            Durak currentDurak = dataService.findDurakById(currentId);

            // Sadece belirtilen taşıma tipine ait duraklara geçiş
            if (currentDurak.stops != null) {
                for (Stop stop : currentDurak.stops) {
                    String neighborId = stop.stopId;
                    Durak neighborDurak = dataService.findDurakById(neighborId);

                    // Sadece belirtilen taşıma tipine ait durakları kullan
                    if (!neighborDurak.type.equals(transportType)) {
                        continue;
                    }

                    double newDistance = distances.get(currentId) + stop.mesafe;
                    double newFare = fares.get(currentId) + stop.ucret;
                    int newDuration = durations.get(currentId) + stop.sure;

                    if (newDistance < distances.getOrDefault(neighborId, Double.MAX_VALUE)) {
                        distances.put(neighborId, newDistance);

                        // Özel gün kontrolü ve ücret hesaplaması
                        if (yolcu.getSpecialDay() != null && yolcu.getSpecialDay()) {
                            // Özel günde ücret 0 olarak ayarlanır
                            fares.put(neighborId, 0.0);
                        } else {
                            // Normal gün - indirim uygulanır
                            double originalFare = newFare;
                            double discount = yolcu.indirim();
                            double discountedFare = originalFare * (1.0 - discount);
                            fares.put(neighborId, discountedFare);
                        }

                        durations.put(neighborId, newDuration);
                        previous.put(neighborId, currentId);
                        queue.add(new NodeDistance(neighborId, newDistance));
                    }
                }
            }

            // Transfer durağına geçiş - sadece aynı taşıma tipi için
            if (currentDurak.transfer != null) {
                String transferId = currentDurak.transfer.transferStopId;
                Durak transferDurak = dataService.findDurakById(transferId);

                // Transfer durağı aynı taşıma tipine ait olmalı
                if (!transferDurak.type.equals(transportType)) {
                    continue;
                }

                double transferDistance = 0.1;
                double transferFare = currentDurak.transfer.ucret;
                int transferDuration = currentDurak.transfer.sure;

                double newDistance = distances.get(currentId) + transferDistance;
                double newFare = fares.get(currentId) + transferFare;
                int newDuration = durations.get(currentId) + transferDuration;

                if (newDistance < distances.getOrDefault(transferId, Double.MAX_VALUE)) {
                    distances.put(transferId, newDistance);

                    // Özel gün kontrolü ve ücret hesaplaması
                    if (yolcu.getSpecialDay() != null && yolcu.getSpecialDay()) {
                        // Özel günde ücret 0 olarak ayarlanır
                        fares.put(transferId, 0.0);
                    } else {
                        // Normal gün - indirim uygulanır
                        double originalFare = newFare;
                        double discount = yolcu.indirim();
                        double discountedFare = originalFare * (1.0 - discount);
                        fares.put(transferId, discountedFare);
                    }

                    durations.put(transferId, newDuration);
                    previous.put(transferId, currentId + "_transfer");
                    queue.add(new NodeDistance(transferId, newDistance));
                }
            }
        }

        if (!previous.containsKey(target.id)) {
            return null;
        }

        return reconstructPath(previous, target.id, fares, yolcu);
    }

    private static class NodeDistance {

        private String nodeId;
        private double distance;

        public NodeDistance(String nodeId, double distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }

        public String getNodeId() {
            return nodeId;
        }

        public double getDistance() {
            return distance;
        }
    }

    private List<RouteStep> reconstructPath(Map<String, String> previous, String targetId, Map<String, Double> fares, Yolcu yolcu) {
        List<RouteStep> path = new ArrayList<>();
        String current = targetId;

        while (previous.containsKey(current)) {
            String prev = previous.get(current);
            boolean isTransfer = false;
            String actualPrev = prev;

            if (prev.endsWith("_transfer")) {
                isTransfer = true;
                actualPrev = prev.substring(0, prev.indexOf("_transfer"));
            }

            Durak currentDurak = dataService.findDurakById(current);
            Durak prevDurak = dataService.findDurakById(actualPrev);

            double fare;
            double distance;
            int duration;

            if (isTransfer) {
                fare = prevDurak.transfer.ucret;
                distance = 0.1;
                duration = prevDurak.transfer.sure;
            } else {
                Stop stopInfo = dataService.findStopInfo(prevDurak, current);
                fare = stopInfo.ucret;
                distance = stopInfo.mesafe;
                duration = stopInfo.sure;
            }

            // Özel gün kontrolü
            if (yolcu.getSpecialDay() != null && yolcu.getSpecialDay()) {
                fare = 0.0;
            } else {
                // İndirim uygula
                double discount = yolcu.indirim();
                fare = fare * (1.0 - discount);
            }

            path.add(0, new RouteStep(
                    prevDurak.type,
                    currentDurak,
                    prevDurak,
                    distance,
                    duration,
                    fare
            ));

            current = actualPrev;
        }

        return path;
    }
}
