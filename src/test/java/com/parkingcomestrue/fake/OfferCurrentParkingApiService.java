package com.parkingcomestrue.fake;

import com.parkingcomestrue.parking.domain.BaseInformation;
import com.parkingcomestrue.parking.domain.Fee;
import com.parkingcomestrue.parking.domain.FeePolicy;
import com.parkingcomestrue.parking.domain.FreeOperatingTime;
import com.parkingcomestrue.parking.domain.Location;
import com.parkingcomestrue.parking.domain.OperatingTime;
import com.parkingcomestrue.parking.domain.OperationType;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.parking.domain.ParkingType;
import com.parkingcomestrue.parking.domain.PayTypes;
import com.parkingcomestrue.parking.domain.Space;
import com.parkingcomestrue.parking.domain.TimeUnit;
import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import java.util.LinkedList;
import java.util.List;

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
    public List<Parking> read() {
        LinkedList<Parking> result = new LinkedList<>();
        for (int i = 0; i < readSize; i++) {
            Parking parking = new Parking(
                    new BaseInformation("offer parking" + i, "02-000" + i, "서울시 어딘가 " + i, PayTypes.DEFAULT,
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

    public void setReadSize(int readSize) {
        this.readSize = readSize;
    }
}
