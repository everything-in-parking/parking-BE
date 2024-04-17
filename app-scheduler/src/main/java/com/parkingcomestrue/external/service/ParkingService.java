package com.parkingcomestrue.external.service;


import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.domain.parking.repository.ParkingRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Transactional
    public void updateParkingLots(Map<String, Parking> parkingLots, Map<String, Parking> saved,
                                  List<Parking> newParkingLots) {
        updateSavedParkingLots(parkingLots, saved);
        saveNewParkingLots(newParkingLots);
    }

    private void updateSavedParkingLots(Map<String, Parking> parkingLots, Map<String, Parking> saved) {
        for (String parkingName : saved.keySet()) {
            Parking origin = saved.get(parkingName);
            Parking updated = parkingLots.get(parkingName);
            origin.update(updated);
        }
    }

    private void saveNewParkingLots(List<Parking> newParkingLots) {
        parkingRepository.saveAll(newParkingLots);
    }
}
