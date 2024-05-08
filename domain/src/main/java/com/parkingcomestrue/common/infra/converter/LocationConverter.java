package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.Location;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.Point;

@Converter
public class LocationConverter implements AttributeConverter<Location, Point> {

    @Override
    public Point convertToDatabaseColumn(Location attribute) {
        return attribute.toPoint();
    }

    @Override
    public Location convertToEntityAttribute(Point dbData) {
        return Location.of(dbData.getX(), dbData.getY());
    }
}
