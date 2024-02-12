package com.example.parking.domain.parking;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface ParkingRepository extends Repository<Parking, Long> {

    Optional<Parking> findById(Long id);

    default Parking getById(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("익셉션 !!"));
    }

    Set<Parking> findAllByName(Set<String> names);

    void saveAll(Iterable<Parking> parkingLots);
}
