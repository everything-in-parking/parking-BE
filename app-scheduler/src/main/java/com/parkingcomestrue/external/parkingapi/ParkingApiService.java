package com.parkingcomestrue.external.parkingapi;

import com.parkingcomestrue.common.domain.parking.Parking;
import java.util.List;

public interface ParkingApiService {

    default boolean offerCurrentParking() {
        return false;
    }

    List<Parking> read() throws Exception;
}
