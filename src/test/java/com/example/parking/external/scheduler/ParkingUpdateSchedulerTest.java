package com.example.parking.external.scheduler;

import com.example.parking.domain.parking.Parking;
import com.example.parking.external.parkingapi.ParkingApiService;
import com.example.parking.fake.BasicParkingRepository;
import com.example.parking.fake.ExceptionParkingApiService;
import com.example.parking.fake.NotOfferCurrentParkingApiService;
import com.example.parking.fake.OfferCurrentParkingApiService;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingUpdateSchedulerTest {

    private OfferCurrentParkingApiService offerCurrentParkingApiService =  new OfferCurrentParkingApiService(5);
    private NotOfferCurrentParkingApiService notOfferCurrentParkingApiService =  new NotOfferCurrentParkingApiService(5);
    private ParkingApiService exceptionParkingApiService = new ExceptionParkingApiService();
    private BasicParkingRepository parkingRepository = new BasicParkingRepository();


    @DisplayName("실시간 주차 대수를 제공하는 API에서 주차장이 0~4까지 저장되어 있는 상태에서 0~9까지 주차장을 읽어와 업데이트한다.")
    @Test
    void autoUpdateOfferCurrentParking() {
        //given
        List<Parking> parkingLots = offerCurrentParkingApiService.read();
        parkingRepository.saveAll(parkingLots);
        int readSize = 10;
        offerCurrentParkingApiService.setReadSize(readSize);

        ParkingUpdateScheduler scheduler = new ParkingUpdateScheduler(
                List.of(offerCurrentParkingApiService),
                null,
                parkingRepository
        );
        //when
        scheduler.autoUpdateOfferCurrentParking();

        //then
        Assertions.assertThat(parkingRepository.count()).isEqualTo(readSize);
    }

    @DisplayName("실시간 주차 대수를 제공하지 않는 API에서 주차장이 0~4까지 저장되어 있는 상태에서 0~9까지 주차장을 읽어와 업데이트한다.")
    @Test
    void autoUpdateNotOfferCurrentParking() {
        //given
        List<Parking> parkingLots = notOfferCurrentParkingApiService.read();
        parkingRepository.saveAll(parkingLots);
        int readSize = 10;
        notOfferCurrentParkingApiService.setReadSize(readSize);

        ParkingUpdateScheduler scheduler = new ParkingUpdateScheduler(
                List.of(notOfferCurrentParkingApiService),
                null,
                parkingRepository
        );
        //when
        scheduler.autoUpdateNotOfferCurrentParking();

        //then
        Assertions.assertThat(parkingRepository.count()).isEqualTo(readSize);
    }

    @DisplayName("실시간 주차 대수를 제공하는 API와 제공하지 않는 API는 영향을 안준다.")
    @Test
    void notAffectBetweenOfferAndNotOfferCurrentParking() {
        //given
        List<Parking> parkingLots = offerCurrentParkingApiService.read();
        parkingRepository.saveAll(parkingLots);
        int readSize = 10;
        notOfferCurrentParkingApiService.setReadSize(readSize);

        ParkingUpdateScheduler scheduler = new ParkingUpdateScheduler(
                List.of(offerCurrentParkingApiService, notOfferCurrentParkingApiService),
                null,
                parkingRepository
        );
        //when
        scheduler.autoUpdateOfferCurrentParking();

        //then
        Assertions.assertThat(parkingRepository.count()).isEqualTo(5);
    }

    @DisplayName("특정 API에서 예외 발생시, 해당 API는 log를 남기고 무시한다.")
    @Test
    void autoUpdateWithExceptionApi() {
        //given
        ParkingUpdateScheduler scheduler = new ParkingUpdateScheduler(
                List.of(offerCurrentParkingApiService, exceptionParkingApiService),
                null,
                parkingRepository
        );
        //when
        scheduler.autoUpdateOfferCurrentParking();

        //then
        Assertions.assertThat(parkingRepository.count()).isEqualTo(5);
    }
}
