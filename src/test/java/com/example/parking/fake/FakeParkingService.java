package com.example.parking.fake;

import com.example.parking.application.parking.ParkingService;

public class FakeParkingService extends ParkingService {

    private final BasicParkingRepository repository;

    public FakeParkingService(BasicParkingRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public int count() {
        return repository.count();
    }
}
