package com.example.parking.fake;

import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicParkingRepository implements ParkingRepository {

    private static Long id = 1L;
    private final Map<Long, Parking> store = new HashMap<>();

    @Override
    public Optional<Parking> findById(Long id) {
        return Optional.of(store.get(id));
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
            store.put(id++, parkingLot);
        }
    }

    public int count() {
        return store.size();
    }
}
