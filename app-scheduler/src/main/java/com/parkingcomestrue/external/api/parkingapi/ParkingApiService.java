package com.parkingcomestrue.external.api.parkingapi;

import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.external.api.HealthChecker;
import java.util.List;

public interface ParkingApiService extends HealthChecker {

    default boolean offerCurrentParking() {
        return false;
    }

    List<Parking> read(int pageNumber, int size);

    int getReadSize();
}
