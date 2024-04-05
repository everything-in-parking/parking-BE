package com.parkingcomestrue.external.scheduler;

import com.parkingcomestrue.parking.application.ParkingService;
import com.parkingcomestrue.parking.domain.Location;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.external.coordinate.CoordinateService;
import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ParkingUpdateScheduler {

    private final List<ParkingApiService> parkingApiServices;
    private final CoordinateService coordinateService;
    private final ParkingService parkingService;

    @Scheduled(cron = "0 */30 * * * *")
    public void autoUpdateOfferCurrentParking() {
        Map<String, Parking> parkingLots = readBy(ParkingApiService::offerCurrentParking);
        Map<String, Parking> saved = findAllByName(parkingLots.keySet());
        updateSavedParkingLots(parkingLots, saved);
        saveNewParkingLots(parkingLots, saved);
    }

    private Map<String, Parking> readBy(Predicate<ParkingApiService> currentParkingAvailable) {
        return parkingApiServices.stream()
                .filter(currentParkingAvailable)
                .map(this::read)
                .flatMap(Collection::stream)
                .collect(toParkingMap());
    }

    private List<Parking> read(ParkingApiService parkingApiService) {
        try {
            return parkingApiService.read();
        } catch (Exception e) {
            log.warn("Error while converting {} to Parking {}", parkingApiService.getClass(), e.getMessage());
            return Collections.emptyList();
        }
    }

    private Collector<Parking, ?, Map<String, Parking>> toParkingMap() {
        return Collectors.toMap(
                parking -> parking.getBaseInformation().getName(),
                Function.identity(),
                (existing, replacement) -> existing
        );
    }

    private Map<String, Parking> findAllByName(Set<String> names) {
        return parkingService.getParkingLots(names)
                .stream()
                .collect(toParkingMap());
    }

    private void updateSavedParkingLots(Map<String, Parking> parkingLots, Map<String, Parking> saved) {
        for (String parkingName : saved.keySet()) {
            Parking origin = saved.get(parkingName);
            Parking updated = parkingLots.get(parkingName);
            origin.update(updated);
        }
    }

    private void saveNewParkingLots(Map<String, Parking> parkingLots, Map<String, Parking> saved) {
        List<Parking> newParkingLots = parkingLots.keySet()
                .stream()
                .filter(parkingName -> !saved.containsKey(parkingName))
                .map(parkingLots::get)
                .toList();
        updateLocation(newParkingLots);
        parkingService.saveAll(newParkingLots);
    }

    private void updateLocation(List<Parking> newParkingLots) {
        for (Parking parking : newParkingLots) {
            Location locationByAddress = coordinateService.extractLocationByAddress(parking.getBaseInformation().getAddress(),
                    parking.getLocation());
            parking.update(locationByAddress);
        }
    }

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.DAYS)
    public void autoUpdateNotOfferCurrentParking() {
        Map<String, Parking> parkingLots = readBy(parkingApiService -> !parkingApiService.offerCurrentParking());
        Map<String, Parking> saved = findAllByName(parkingLots.keySet());
        updateSavedParkingLots(parkingLots, saved);
        saveNewParkingLots(parkingLots, saved);
    }
}
