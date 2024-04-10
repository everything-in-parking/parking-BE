package com.parkingcomestrue.fake;

import com.parkingcomestrue.external.coordinate.CoordinateApiService;
import com.parkingcomestrue.parking.domain.parking.Location;

public class FakeCoordinateApiService extends CoordinateApiService {

    public FakeCoordinateApiService() {
        super(null);
    }

    @Override
    public Location extractLocationByAddress(String address, Location location) {
        return Location.of(10.0, 10.0);
    }
}
