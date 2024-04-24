package repository;

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
import com.parkingcomestrue.common.domain.parking.repository.ParkingRepository;
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
                    new BaseInformation("not offer parking" + i, "051-000" + i, "부산시 어딘가 " + i,
                            List.of(PayType.NO_INFO),
                            ParkingType.NO_INFO,
                            OperationType.PUBLIC),
                    Location.of("133.333" + i, "44.444" + i),
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
