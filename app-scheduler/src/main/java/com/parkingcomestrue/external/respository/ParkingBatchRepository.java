package com.parkingcomestrue.external.respository;

import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.domain.parking.repository.ParkingRepository;
import java.util.List;

public interface ParkingBatchRepository extends ParkingRepository {

    void saveWithBatch(List<Parking> parkingLots);
}
