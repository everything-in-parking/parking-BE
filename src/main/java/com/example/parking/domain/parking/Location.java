package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

    private static final Location NO_PROVIDE = new Location(-1.0, -1.0);

    private double longitude;
    private double latitude;

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static Location of(String longitude, String latitude) {
        try {
            return new Location(Double.parseDouble(longitude), Double.parseDouble(latitude));
        } catch (NumberFormatException | NullPointerException e) {
            return NO_PROVIDE;
        }
    }
}
