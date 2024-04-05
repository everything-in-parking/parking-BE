package com.parkingcomestrue.fake;

import com.parkingcomestrue.searchcondition.application.SearchConditionMapper;
import com.parkingcomestrue.parking.application.ParkingFilteringService;
import com.parkingcomestrue.parking.application.ParkingService;
import com.parkingcomestrue.review.application.ReviewService;
import com.parkingcomestrue.parking.domain.ParkingFeeCalculator;
import com.parkingcomestrue.review.service.ReviewDomainService;

public class FakeParkingService extends ParkingService {

    private final BasicParkingRepository repository;

    public FakeParkingService(BasicParkingRepository repository) {
        super(
                repository,
                new ParkingFilteringService(new ParkingFeeCalculator()),
                new FakeFavoriteRepository(),
                new SearchConditionMapper(),
                new ParkingFeeCalculator(),
                new ReviewService(
                        new BasicReviewRepository(),
                        new ReviewDomainService(new BasicReviewRepository())
                ));
        this.repository = repository;
    }

    public int count() {
        return repository.count();
    }
}
