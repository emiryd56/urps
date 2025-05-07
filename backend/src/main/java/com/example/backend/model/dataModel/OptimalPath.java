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

public class OptimalPath {

    private Data data;
    private DataService dataService;

    public OptimalPath(DataService dataService) {
        this.dataService = dataService;
        this.data = dataService.getCityData();
    }

    public List<RouteStep> findOptimalPath(Durak start, Durak target, Yolcu yolcu) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Map<String, Double> fares = new HashMap<>();
        Map<String, Integer> durations = new HashMap<>();
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(
                Comparator.comparingDouble(NodeDistance::getDistance));
        Set<String> visited = new HashSet<>();

        for (Durak durak : data.duraklar) {
            distances.put(durak.id, Double.MAX_VALUE);
            fares.put(durak.id, Double.MAX_VALUE);
            durations.put(durak.id, Integer.MAX_VALUE);
        }

        // Başlangıç durağının mesafesini 0 olarak belirle
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

            // Komşu duraklara geçiş
            if (currentDurak.stops != null) {
                for (Stop stop : currentDurak.stops) {
                    String neighborId = stop.stopId;
                    double newDistance = distances.get(currentId) + stop.mesafe;
                    double newFare = fares.get(currentId) + stop.ucret;
                    int newDuration = durations.get(currentId) + stop.sure;

                    if (newDistance < distances.getOrDefault(neighborId, Double.MAX_VALUE)) {
                        distances.put(neighborId, newDistance);
                        double originalFare = newFare;
                        double discount = yolcu.indirim();
                        double discountedFare = originalFare * (1.0 - discount);
                        fares.put(neighborId, discountedFare);
                        durations.put(neighborId, newDuration);
                        previous.put(neighborId, currentId);
                        queue.add(new NodeDistance(neighborId, newDistance));
                    }
                }
            }

            // Transfer durağına geçiş
            if (currentDurak.transfer != null) {
                String transferId = currentDurak.transfer.transferStopId;
                double transferDistance = 0.1;
                double transferFare = currentDurak.transfer.ucret;
                int transferDuration = currentDurak.transfer.sure;

                double newDistance = distances.get(currentId) + transferDistance;
                double newFare = fares.get(currentId) + transferFare;
                int newDuration = durations.get(currentId) + transferDuration;

                if (newDistance < distances.getOrDefault(transferId, Double.MAX_VALUE)) {
                    distances.put(transferId, newDistance);
                    double originalFare = newFare;
                    double discount = yolcu.indirim();
                    double discountedFare = originalFare * (1.0 - discount);
                    fares.put(transferId, discountedFare);
                    durations.put(transferId, newDuration);
                    previous.put(transferId, currentId + "_transfer");
                    queue.add(new NodeDistance(transferId, newDistance));
                }
            }
        }

        // Eğer hedef durağa ulaşılamadıysa null döndür
        if (!previous.containsKey(target.id)) {
            return null;
        }

        // Oluşturulan yolu geri izle
        List<RouteStep> path = new ArrayList<>();
        String current = target.id;

        while (previous.containsKey(current)) {
            String prev = previous.get(current);
            Durak currentDurak = dataService.findDurakById(current);

            if (prev.endsWith("_transfer")) {
                // Transfer adımını ekle
                String prevId = prev.substring(0, prev.indexOf("_transfer"));
                Durak prevDurak = dataService.findDurakById(prevId);
                double transferFare = prevDurak.transfer.ucret;
                double discountedFare = transferFare * (1.0 - yolcu.indirim());

                path.add(0, new RouteStep(
                        "transfer",
                        prevDurak,
                        currentDurak,
                        0.1,
                        prevDurak.transfer.sure,
                        discountedFare
                ));

                current = prevId;
            } else {
                // Normal adımı ekle
                Durak prevDurak = dataService.findDurakById(prev);
                Stop stopInfo = dataService.findStopInfo(prevDurak, current);
                double stopFare = stopInfo.ucret;
                double discountedFare = stopFare * (1.0 - yolcu.indirim());

                path.add(0, new RouteStep(
                        prevDurak.type,
                        prevDurak,
                        currentDurak,
                        stopInfo.mesafe,
                        stopInfo.sure,
                        discountedFare
                ));

                current = prev;
            }
        }

        return path;
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

}
