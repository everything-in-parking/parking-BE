package com.example.parking.external.scheduler;

import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingRepository;
import com.example.parking.external.parkingapi.ParkingApiService;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@Service
public class ParkingUpdateScheduler {

    private final List<ParkingApiService> parkingApiServices;
    private final ParkingRepository parkingRepository;

    public ParkingUpdateScheduler(List<ParkingApiService> parkingApiServices, ParkingRepository parkingRepository) {
        this.parkingApiServices = parkingApiServices;
        this.parkingRepository = parkingRepository;
    }

    /**
     * todo
     * 새로 생성되는 데이터의 경우, 주소에서 필요한 정보(좌표) 정확하게 변환
     */
    @Transactional
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
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
                .collect(Collectors.toMap(
                        parking -> parking.getBaseInformation().getName(),
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
    }

    private Map<String, Parking> findAllByName(Set<String> names) {
        return parkingRepository.findAllByBaseInformationNameIn(names)
                .stream()
                .collect(Collectors.toMap(parking -> parking.getBaseInformation().getName(), Function.identity()));
    }

    private List<Parking> read(ParkingApiService parkingApiService) {
        try {
            return parkingApiService.read();
        } catch (Exception e) {
            log.warn("Error while converting {} to Parking {}", parkingApiService.getClass(), e.getMessage());
            return Collections.emptyList();
        }
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

        parkingRepository.saveAll(newParkingLots);
    }

    @Transactional
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.DAYS)
    public void autoUpdateNotOfferCurrentParking() {
        Map<String, Parking> parkingLots = readBy(parkingApiService -> !parkingApiService.offerCurrentParking());
        Map<String, Parking> saved = findAllByName(parkingLots.keySet());
        updateSavedParkingLots(parkingLots, saved);
        saveNewParkingLots(parkingLots, saved);
    }
}
