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
import com.parkingcomestrue.parking.repository.ParkingRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.locationtech.jts.geom.Point;

public class BasicParkingRepository implements ParkingRepository, BasicRepository<Parking, Long> {

    private static Long id = 1L;
    private final Map<Long, Parking> store = new HashMap<>();

    @Override
    public Optional<Parking> findById(Long id) {
        return Optional.of(store.get(id));
    }

    @Override
    public List<Parking> findAroundParkingLots(Point point, int radius) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Parking> findAroundParkingLotsOrderByDistance(Point point, int radius) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Set<Parking> findAllByBaseInformationNameIn(Set<String> names) {
        return store.values()
                .stream()
                .filter(parking -> names.contains(parking.getBaseInformation().getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public void saveAll(Iterable<Parking> parkingLots) {
        for (Parking parkingLot : parkingLots) {
            save(parkingLot);
        }
    }

    public void save(Parking parkingLot) {
        setId(parkingLot, id);
        store.put(id++, parkingLot);
    }

    public List<Parking> saveAndGet(int size) {
        LinkedList<Parking> result = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            Parking parking = new Parking(
                    new BaseInformation("not offer parking" + i, "051-000" + i, "부산시 어딘가 " + i, PayTypes.DEFAULT,
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
        saveAll(result);
        return result;
    }

    public int count() {
        return store.size();
    }
}
