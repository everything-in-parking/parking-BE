package com.parkingcomestrue.fake;

import com.parkingcomestrue.external.api.parkingapi.HealthCheckResponse;
import com.parkingcomestrue.external.support.exception.SchedulerException;
import com.parkingcomestrue.external.support.exception.SchedulerExceptionInformation;
import com.parkingcomestrue.external.api.parkingapi.ParkingApiService;
import com.parkingcomestrue.common.domain.parking.Parking;
import java.util.List;

public class ExceptionParkingApiService implements ParkingApiService {

    @Override
    public boolean offerCurrentParking() {
        return true;
    }

    @Override
    public List<Parking> read(int pageNumber, int size) {
        throw new SchedulerException(SchedulerExceptionInformation.INVALID_CONNECT);
    }

    @Override
    public int getReadSize() {
        return 0;
    }

    @Override
    public HealthCheckResponse check() {
        return new HealthCheckResponse(false, 0);
    }
}
