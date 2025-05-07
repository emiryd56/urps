package com.example.backend.controller;

import com.example.backend.model.dataModel.RouteReq;
import com.example.backend.model.dataModel.RouteResponse;
import com.example.backend.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rota")
public class RouteController {

    public final RouteService routeService;
    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }

    @PostMapping("/en-uygun")
    public ResponseEntity<RouteResponse> calculateRoute(@RequestBody RouteReq request) {
        RouteResponse response = routeService.createUserAndResponse(request);
        return ResponseEntity.ok(response);
    }
}
