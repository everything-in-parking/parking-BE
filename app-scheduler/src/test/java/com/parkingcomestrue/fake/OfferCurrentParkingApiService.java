package com.parkingcomestrue.fake;

import com.parkingcomestrue.common.domain.parking.BaseInformation;
import com.parkingcomestrue.common.domain.parking.Fee;
import com.parkingcomestrue.common.domain.parking.FeePolicy;
import com.parkingcomestrue.common.domain.parking.FreeOperatingTime;
import com.parkingcomestrue.common.domain.parking.Location;
import com.parkingcomestrue.common.domain.parking.OperatingTime;
import com.parkingcomestrue.common.domain.parking.OperationType;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.domain.parking.ParkingType;
import com.parkingcomestrue.common.domain.parking.PayType;
import com.parkingcomestrue.common.domain.parking.Space;
import com.parkingcomestrue.common.domain.parking.TimeUnit;
import com.parkingcomestrue.external.api.HealthCheckResponse;
import com.parkingcomestrue.external.api.parkingapi.ParkingApiService;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class OfferCurrentParkingApiService implements ParkingApiService {

    private int readSize;

    public OfferCurrentParkingApiService(int readSize) {
        this.readSize = readSize;
    }

    @Override
    public boolean offerCurrentParking() {
        return true;
    }

    @Override
    public List<Parking> read(int pageNumber, int size) {
        LinkedList<Parking> result = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            Parking parking = new Parking(
                    new BaseInformation("offer parking" + i, "02-000" + i, "서울시 어딘가 " + i, Set.of(PayType.NO_INFO),
                            ParkingType.NO_INFO,
                            OperationType.PUBLIC),
                    Location.of("11.111" + i, "22.222" + i),
                    Space.of(100, 10),
                    FreeOperatingTime.ALWAYS_FREE,
                    OperatingTime.ALWAYS_OPEN,
                    new FeePolicy(Fee.ZERO, Fee.ZERO, TimeUnit.from(0), TimeUnit.from(0), Fee.ZERO)
            );
            result.add(parking);
        }

        return result;
    }

    @Override
    public int getReadSize() {
        return readSize;
    }

    public void setReadSize(int readSize) {
        this.readSize = readSize;
    }

    @Override
    public HealthCheckResponse check() {
        return new HealthCheckResponse(true, readSize);
    }
}
