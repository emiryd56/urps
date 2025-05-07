package com.example.backend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

import com.example.backend.model.dataModel.*;
import org.springframework.stereotype.Service;

import com.example.backend.model.passenger.Genel;
import com.example.backend.model.passenger.Ogrenci;
import com.example.backend.model.passenger.Yasli;
import com.example.backend.model.passenger.Yolcu;
import com.example.backend.model.payment.KentKart;
import com.example.backend.model.payment.KrediKarti;
import com.example.backend.model.payment.Nakit;
import com.example.backend.model.payment.Payment;

@Service
public class RouteService {
    private FindBestRoute findBestRoute;
    public RouteService(DataService dataService) {
        findBestRoute = new FindBestRoute(dataService);
    }
    public RouteResponse createUserAndResponse(RouteReq request) {
        Yolcu yolcu = null;
        Payment payment = null;
        if (Objects.equals(request.getPaymentType(), "cash")) {
            payment = new Nakit("Nakit", request.getWalletBalance());
        }
        if (Objects.equals(request.getPaymentType(), "creditCard")) {
            payment = new KrediKarti("KrediKarti", request.getWalletBalance());
        }
        if (Objects.equals(request.getPaymentType(), "kentkart")) {
            payment = new KentKart("kentkart", request.getWalletBalance());
        }
        if (Objects.equals(request.getUserType(), "general")) {

            yolcu = new Genel(request.getUserType(), request.getEnd(), request.getStart(), payment, request.getSpecialDay());
        }
        if (Objects.equals(request.getUserType(), "student")) {

            yolcu = new Ogrenci(request.getUserType(), request.getEnd(), request.getStart(), payment, request.getSpecialDay());
        }
        if (Objects.equals(request.getUserType(), "elderly")) {

            yolcu = new Yasli(request.getUserType(), request.getEnd(), request.getStart(), payment, request.getSpecialDay());
        }
        return findBestRoute.calculate(yolcu);

    }
}
