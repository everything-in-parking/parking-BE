package com.parkingcomestrue.parking.application;

import com.parkingcomestrue.parking.application.dto.SearchingCondition;
import com.parkingcomestrue.searchcondition.application.SearchConditionMapper;
import com.parkingcomestrue.parking.application.dto.ParkingDetailInfoResponse;
import com.parkingcomestrue.parking.application.dto.ParkingDetailInfoResponse.FeeInfo;
import com.parkingcomestrue.parking.application.dto.ParkingDetailInfoResponse.HolidayOperatingTime;
import com.parkingcomestrue.parking.application.dto.ParkingDetailInfoResponse.SaturdayOperatingTime;
import com.parkingcomestrue.parking.application.dto.ParkingDetailInfoResponse.WeekdayOperatingTime;
import com.parkingcomestrue.parking.application.dto.ParkingLotsResponse;
import com.parkingcomestrue.parking.application.dto.ParkingLotsResponse.ParkingResponse;
import com.parkingcomestrue.parking.application.dto.ParkingQueryRequest;
import com.parkingcomestrue.parking.application.dto.ParkingSearchConditionRequest;
import com.parkingcomestrue.review.application.ReviewService;
import com.parkingcomestrue.review.application.dto.ReviewInfoResponse;
import com.parkingcomestrue.favorite.domain.Favorite;
import com.parkingcomestrue.favorite.domain.FavoriteRepository;
import com.parkingcomestrue.parking.domain.Fee;
import com.parkingcomestrue.parking.domain.Location;
import com.parkingcomestrue.parking.domain.OperationType;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.parking.domain.ParkingFeeCalculator;
import com.parkingcomestrue.parking.domain.ParkingType;
import com.parkingcomestrue.parking.domain.PayType;
import com.parkingcomestrue.parking.repository.ParkingRepository;
import com.parkingcomestrue.searchcondition.domain.FeeType;
import com.parkingcomestrue.support.Association;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ParkingService {

    private static final String DISTANCE_ORDER_CONDITION = "가까운 순";

    private final ParkingRepository parkingRepository;
    private final ParkingFilteringService parkingFilteringService;
    private final FavoriteRepository favoriteRepository;
    private final SearchConditionMapper searchConditionMapper;
    private final ParkingFeeCalculator parkingFeeCalculator;
    private final ReviewService reviewService;

    @Transactional(readOnly = true)
    public ParkingLotsResponse findParkingLots(ParkingQueryRequest parkingQueryRequest,
                                               ParkingSearchConditionRequest parkingSearchConditionRequest,
                                               Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        Location destination = Location.of(parkingQueryRequest.getLongitude(), parkingQueryRequest.getLatitude());

        // 반경 주차장 조회
        List<Favorite> favorites = findMemberFavorites(memberId);
        List<Parking> parkingLots = findParkingLotsByOrderCondition(parkingSearchConditionRequest.getPriority(),
                parkingQueryRequest, destination);

        // 조회조건 기반 필터링
        SearchingCondition searchingCondition = toSearchingCondition(parkingSearchConditionRequest);
        List<Parking> filteredParkingLots = parkingFilteringService.filterByCondition(parkingLots, searchingCondition,
                now);

        // 응답 dto 변환
        List<ParkingResponse> parkingResponses = collectParkingInfo(filteredParkingLots,
                parkingSearchConditionRequest.getHours(), destination, favorites, now);

        return new ParkingLotsResponse(parkingResponses);
    }

    private List<Favorite> findMemberFavorites(Long memberId) {
        if (memberId == null) {
            return Collections.emptyList();
        }
        return favoriteRepository.findByMemberId(Association.from(memberId));
    }

    private List<Parking> findParkingLotsByOrderCondition(String priority, ParkingQueryRequest parkingQueryRequest,
                                                          Location middleLocation) {
        if (priority.equals(DISTANCE_ORDER_CONDITION)) {
            return parkingRepository.findAroundParkingLotsOrderByDistance(middleLocation.getPoint(),
                    parkingQueryRequest.getRadius());
        }
        return parkingRepository.findAroundParkingLots(middleLocation.getPoint(), parkingQueryRequest.getRadius());
    }

    private SearchingCondition toSearchingCondition(ParkingSearchConditionRequest request) {
        List<ParkingType> parkingTypes = searchConditionMapper.toEnums(ParkingType.class, request.getParkingTypes());
        List<OperationType> operationTypes = searchConditionMapper.toEnums(OperationType.class,
                request.getOperationTypes());
        List<PayType> payTypes = searchConditionMapper.toEnums(PayType.class, request.getPayTypes());
        FeeType feeType = searchConditionMapper.toEnum(FeeType.class, request.getFeeType());

        return new SearchingCondition(operationTypes, parkingTypes, payTypes, feeType, request.getHours());
    }

    private List<ParkingResponse> collectParkingInfo(List<Parking> parkingLots, int hours,
                                                     Location destination, List<Favorite> memberFavorites,
                                                     LocalDateTime now) {
        Set<Long> favoriteParkingIds = extractFavoriteParkingIds(memberFavorites);
        return calculateParkingInfo(parkingLots, destination, hours, favoriteParkingIds, now);
    }

    private List<ParkingResponse> calculateParkingInfo(List<Parking> parkingLots, Location destination, int hours,
                                                       Set<Long> favoriteParkingIds, LocalDateTime now) {
        return parkingLots.stream()
                .map(parking -> toParkingResponse(
                                parking,
                                parkingFeeCalculator.calculateParkingFee(parking, now, now.plusHours(hours)),
                                parking.calculateWalkingTime(destination),
                                favoriteParkingIds.contains(parking.getId())
                        )
                ).toList();
    }

    private Set<Long> extractFavoriteParkingIds(List<Favorite> memberFavorites) {
        return memberFavorites.stream()
                .map(Favorite::getParkingId)
                .map(Association::getId)
                .collect(Collectors.toSet());
    }

    private ParkingResponse toParkingResponse(Parking parking, Fee fee, int walkingTime, boolean isFavorite) {
        return new ParkingResponse(
                parking.getId(),
                parking.getBaseInformation().getName(),
                fee.getFee(),
                walkingTime,
                parking.getBaseInformation().getParkingType().getDescription(),
                isFavorite,
                parking.getLocation().getLatitude(),
                parking.getLocation().getLongitude()
        );
    }

    @Transactional
    public void saveAll(List<Parking> parkingLots) {
        parkingRepository.saveAll(parkingLots);
    }

    @Transactional(readOnly = true)
    public Set<Parking> getParkingLots(Set<String> parkingNames) {
        return parkingRepository.findAllByBaseInformationNameIn(parkingNames);
    }

    @Transactional(readOnly = true)
    public ParkingDetailInfoResponse findParking(Long parkingId) {
        LocalDateTime now = LocalDateTime.now();
        Parking parking = parkingRepository.getById(parkingId);
        ReviewInfoResponse reviews = reviewService.readReviews(parkingId);
        int diffMinute = parking.calculateUpdatedDiff(now);

        return toParkingResponse(reviews, parking, diffMinute);
    }

    private ParkingDetailInfoResponse toParkingResponse(ReviewInfoResponse reviews, Parking parking, int diffMinute) {
        return new ParkingDetailInfoResponse(
                parking.getBaseInformation().getName(),
                parking.getBaseInformation().getParkingType().getDescription(),
                parking.getLocation().getLatitude(),
                parking.getLocation().getLongitude(),
                new FeeInfo(
                        parking.getFeePolicy().getBaseFee().getFee(),
                        parking.getFeePolicy().getBaseTimeUnit().getTimeUnit()
                ),
                parking.getSpace().getCurrentParking(),
                parking.getSpace().getCapacity(),
                diffMinute,
                parking.getBaseInformation().getTel(),
                parking.getBaseInformation().getPayTypes().getDescription(),
                new WeekdayOperatingTime(
                        parking.getOperatingTime().getWeekdayBeginTime(),
                        parking.getOperatingTime().getWeekdayEndTime()),
                new SaturdayOperatingTime(
                        parking.getOperatingTime().getSaturdayBeginTime(),
                        parking.getOperatingTime().getSaturdayEndTime()),
                new HolidayOperatingTime(
                        parking.getOperatingTime().getHolidayBeginTime(),
                        parking.getOperatingTime().getHolidayEndTime()),
                reviews
        );
    }
}
