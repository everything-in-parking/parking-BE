package com.parkingcomestrue.common.domain.parking;

import com.parkingcomestrue.common.support.exception.DomainException;
import com.parkingcomestrue.common.support.exception.DomainExceptionInformation;
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
    public static final Location NO_PROVIDE = new Location(-1.0, -1.0);

    private static final Double MAX_LONGITUDE = 180.0;
    private static final Double MIN_LONGITUDE = -180.0;

    private static final Double MAX_LATITUDE = 90.0;
    private static final Double MIN_LATITUDE = -90.0;

    private Double longitude;
    private Double latitude;

    private Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static Location of(Double longitude, Double latitude) {
        try {
            verifyLocation(longitude, latitude);
            return new Location(longitude, latitude);
        } catch (NullPointerException | DomainException e) {
            return NO_PROVIDE;
        }
    }

    private static void verifyLocation(Double longitude, Double latitude) {
        if (longitude > MAX_LONGITUDE || longitude < MIN_LONGITUDE || latitude > MAX_LATITUDE
                || latitude < MIN_LATITUDE) {
            throw new DomainException(DomainExceptionInformation.INVALID_LOCATION);
        }
    }

    public static Location of(String longitude, String latitude) {
        try {
            return Location.of(Double.parseDouble(longitude), Double.parseDouble(latitude));
        } catch (NumberFormatException | NullPointerException e) {
            return NO_PROVIDE;
        }
    }

    public Point toPoint() {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
