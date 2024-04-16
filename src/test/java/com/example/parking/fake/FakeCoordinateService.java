package com.example.parking.fake;

import com.example.parking.domain.parking.Location;
import com.example.parking.external.coordinate.CoordinateService;

public class FakeCoordinateService extends CoordinateService {

    public FakeCoordinateService() {
        super(null);
    }

    @Override
    public Location extractLocationByAddress(String address, Location location) {
        return Location.of(10.0, 10.0);
    }
}
