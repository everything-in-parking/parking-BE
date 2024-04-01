package com.example.parking.fake;

import com.example.parking.application.SearchConditionMapper;
import com.example.parking.application.parking.ParkingFilteringService;
import com.example.parking.application.parking.ParkingService;
import com.example.parking.domain.parking.ParkingFeeCalculator;

public class FakeParkingService extends ParkingService {

    private final BasicParkingRepository repository;

    public FakeParkingService(BasicParkingRepository repository) {
        super(repository, new ParkingFilteringService(new ParkingFeeCalculator()),
                new FakeFavoriteRepository(), new SearchConditionMapper(), new ParkingFeeCalculator());
        this.repository = repository;
    }

    public int count() {
        return repository.count();
    }
}
