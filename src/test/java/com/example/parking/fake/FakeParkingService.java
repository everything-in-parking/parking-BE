package com.example.parking.fake;

import com.example.parking.application.parking.ParkingDomainService;
import com.example.parking.application.parking.ParkingService;

public class FakeParkingService extends ParkingService {

    private final BasicParkingRepository repository;

    public FakeParkingService(BasicParkingRepository repository) {
        super(repository, new ParkingDomainService());
        this.repository = repository;
    }

    public int count() {
        return repository.count();
    }
}
