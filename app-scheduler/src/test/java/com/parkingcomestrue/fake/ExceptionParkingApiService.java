package com.parkingcomestrue.fake;

import com.parkingcomestrue.external.support.exception.SchedulerException;
import com.parkingcomestrue.external.support.exception.SchedulerExceptionInformation;
import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import com.parkingcomestrue.common.domain.parking.Parking;
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
