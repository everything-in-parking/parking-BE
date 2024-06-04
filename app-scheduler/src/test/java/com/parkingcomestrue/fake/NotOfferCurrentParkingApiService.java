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
import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NotOfferCurrentParkingApiService implements ParkingApiService {

    private int readSize;

    public NotOfferCurrentParkingApiService(int readSize) {
        this.readSize = readSize;
    }

    @Override
    public boolean offerCurrentParking() {
        return false;
    }

    @Override
    public List<Parking> read() {
        LinkedList<Parking> result = new LinkedList<>();
        for (int i = 0; i < readSize; i++) {
            Parking parking = new Parking(
                    new BaseInformation("not offer parking" + i, "051-000" + i, "부산시 어딘가 " + i,
                            Set.of(PayType.NO_INFO),
                            ParkingType.NO_INFO,
                            OperationType.PUBLIC),
                    Location.of("33.333" + i, "44.444" + i),
                    Space.of(-1, -1),
                    FreeOperatingTime.ALWAYS_FREE,
                    OperatingTime.ALWAYS_OPEN,
                    new FeePolicy(Fee.ZERO, Fee.ZERO, TimeUnit.from(0), TimeUnit.from(0), Fee.ZERO)
            );
            result.add(parking);
        }

        return result;
    }

    public void setReadSize(int readSize) {
        this.readSize = readSize;
    }
}
