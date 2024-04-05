package com.parkingcomestrue.fake;

import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import com.parkingcomestrue.support.exception.ClientException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
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
