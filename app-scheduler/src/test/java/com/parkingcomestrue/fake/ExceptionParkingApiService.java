package com.parkingcomestrue.fake;

import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import com.parkingcomestrue.parking.domain.parking.Parking;
import com.parkingcomestrue.parking.support.exception.ClientException;
import com.parkingcomestrue.parking.support.exception.ExceptionInformation;
import java.util.List;

public class ExceptionParkingApiService implements ParkingApiService {

    @Override
    public boolean offerCurrentParking() {
        return true;
    }

    @Override
    public List<Parking> read() {
        throw new ClientException(ExceptionInformation.INVALID_CONNECT);
    }
}
