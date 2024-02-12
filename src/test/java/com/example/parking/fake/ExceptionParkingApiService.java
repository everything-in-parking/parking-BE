package com.example.parking.fake;

import com.example.parking.domain.parking.Parking;
import com.example.parking.external.parkingapi.ParkingApiService;
import java.util.List;

public class ExceptionParkingApiService implements ParkingApiService {

    @Override
    public boolean offerCurrentParking() {
        return true;
    }

    @Override
    public List<Parking> read() {
        throw new IllegalStateException("API 연결중 에러");
    }
}
