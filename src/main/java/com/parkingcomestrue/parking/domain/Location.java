package com.parkingcomestrue.parking.domain;

import com.parkingcomestrue.support.exception.DomainException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private static final Location NO_PROVIDE = new Location(geometryFactory.createPoint(new Coordinate(-1.0, -1.0)));

    private static final Double MAX_LONGITUDE = 180.0;
    private static final Double MIN_LONGITUDE = -180.0;

    private static final Double MAX_LATITUDE = 90.0;
    private static final Double MIN_LATITUDE = -90.0;

    private Point point;

    private Location(Point point) {
        this.point = point;
    }

    public static Location of(Double longitude, Double latitude) {
        try {
            verifyLocation(longitude, latitude);
            Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
            return new Location(point);
        } catch (NullPointerException e) {
            return NO_PROVIDE;
        }
    }

    private static void verifyLocation(Double longitude, Double latitude) {
        if (longitude > MAX_LONGITUDE || longitude < MIN_LONGITUDE || latitude > MAX_LATITUDE
                || latitude < MIN_LATITUDE) {
            throw new DomainException(ExceptionInformation.INVALID_LOCATION);
        }
    }

    public static Location of(String longitude, String latitude) {
        try {
            return Location.of(Double.parseDouble(longitude), Double.parseDouble(latitude));
        } catch (NumberFormatException | NullPointerException e) {
            return NO_PROVIDE;
        }
    }

    public double getLongitude() {
        return point.getX();
    }

    public double getLatitude() {
        return point.getY();
    }
}
