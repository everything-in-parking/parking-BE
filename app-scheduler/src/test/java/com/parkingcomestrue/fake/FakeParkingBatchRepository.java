package com.parkingcomestrue.fake;

import com.parkingcomestrue.external.respository.ParkingBatchRepository;
import com.parkingcomestrue.common.domain.parking.Parking;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.locationtech.jts.geom.Point;
import repository.BasicParkingRepository;

public class FakeParkingBatchRepository implements ParkingBatchRepository {

    BasicParkingRepository parkingRepository = new BasicParkingRepository();

    @Override
    public void saveWithBatch(List<Parking> parkingLots) {
        parkingRepository.saveAll(parkingLots);
    }

    @Override
    public Optional<Parking> findById(Long id) {
        return parkingRepository.findById(id);
    }

    @Override
    public List<Parking> findAroundParkingLots(Point point, int radius) {
        return parkingRepository.findAroundParkingLots(point, radius);
    }

    @Override
    public List<Parking> findAroundParkingLotsOrderByDistance(Point point, int radius) {
        return parkingRepository.findAroundParkingLotsOrderByDistance(point, radius);
    }

    @Override
    public Set<Parking> findAllByBaseInformationNameIn(Set<String> parkingNames) {
        return parkingRepository.findAllByBaseInformationNameIn(parkingNames);
    }

    @Override
    public void saveAll(Iterable<Parking> parkingLots) {
        parkingRepository.saveAll(parkingLots);
    }

    public int count() {
        return parkingRepository.count();
    }
}
