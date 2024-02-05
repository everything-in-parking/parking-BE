package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

    private double longitude;
    private double latitude;

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
