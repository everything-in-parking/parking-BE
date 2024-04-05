package com.example.parking.application.parking.dto;

import lombok.Getter;

@Getter
public class ParkingQueryRequest {

    private final Double longitude;
    private final Double latitude;
    private final Integer radius;

    public ParkingQueryRequest(Double longitude, Double latitude, Integer radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }
}
