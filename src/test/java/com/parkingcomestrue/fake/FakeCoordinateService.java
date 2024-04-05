package com.parkingcomestrue.fake;

import com.parkingcomestrue.parking.domain.Location;
import com.parkingcomestrue.external.coordinate.CoordinateService;

public class FakeCoordinateService extends CoordinateService {

    public FakeCoordinateService() {
        super(null);
    }

    @Override
    public Location extractLocationByAddress(String address, Location location) {
        return Location.of(10.0, 10.0);
    }
}
