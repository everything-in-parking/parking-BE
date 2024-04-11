package com.parkingcomestrue.fake;

import com.parkingcomestrue.external.exception.SchedulerException;
import com.parkingcomestrue.external.exception.SchedulerExceptionInformation;
import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import com.parkingcomestrue.parking.domain.parking.Parking;
import java.util.List;

public class ExceptionParkingApiService implements ParkingApiService {

    @Override
    public boolean offerCurrentParking() {
        return true;
    }

    @Override
    public List<Parking> read() {
        throw new SchedulerException(SchedulerExceptionInformation.INVALID_CONNECT);
    }
}
