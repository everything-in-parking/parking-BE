package com.parkingcomestrue.common.domain.parking;

import com.parkingcomestrue.common.domain.AuditingEntity;
import com.parkingcomestrue.common.infra.converter.LocationConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parking extends AuditingEntity {

    private static final int MINUTE_UNIT = 60;

    private static final int AVERAGE_WALKING_SPEED = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BaseInformation baseInformation;

    @Convert(converter = LocationConverter.class)
    private Location location;

    @Embedded
    private Space space;

    @Embedded
    private FreeOperatingTime freeOperatingTime;

    @Embedded
    private OperatingTime operatingTime;

    @Embedded
    private FeePolicy feePolicy;

    @Builder
    private Parking(Long id, BaseInformation baseInformation, Location location, Space space,
                    FreeOperatingTime freeOperatingTime, OperatingTime operatingTime, FeePolicy feePolicy) {
        this.id = id;
        this.baseInformation = baseInformation;
        this.location = location;
        this.space = space;
        this.freeOperatingTime = freeOperatingTime;
        this.operatingTime = operatingTime;
        this.feePolicy = feePolicy;
    }

    public Parking(BaseInformation baseInformation, Location location, Space space,
                   FreeOperatingTime freeOperatingTime, OperatingTime operatingTime, FeePolicy feePolicy) {
        this.baseInformation = baseInformation;
        this.location = location;
        this.space = space;
        this.freeOperatingTime = freeOperatingTime;
        this.operatingTime = operatingTime;
        this.feePolicy = feePolicy;
    }

    public int calculatePayOfChargeMinutes(DayParking dayParking) {
        return freeOperatingTime.calculateNonFreeUsageMinutes(dayParking);
    }

    public Fee calculateParkingFee(int payOfChargeMinutes) {
        return feePolicy.calculateFee(payOfChargeMinutes);
    }

    public boolean supportCalculateParkingFee() {
        return feePolicy.supportBase() && feePolicy.supportExtra();
    }

    public void update(Parking updated) {
        this.space = updated.space;
        this.freeOperatingTime = updated.freeOperatingTime;
        this.operatingTime = updated.operatingTime;
        this.feePolicy = updated.feePolicy;
    }

    public void update(Location location) {
        this.location = location;
    }

    public boolean containsOperationType(Set<OperationType> operationTypes) {
        return baseInformation.containsOperationType(operationTypes);
    }

    public boolean containsParkingType(Set<ParkingType> parkingTypes) {
        return baseInformation.containsParkingType(parkingTypes);
    }

    public boolean containsPayType(Set<PayType> memberPayTypes) {
        return baseInformation.containsPayType(memberPayTypes);
    }

    public int calculateWalkingTime(Location destination) {
        double distance = calculateDistanceToDestination(destination);
        double averageWalkingTime = distance / AVERAGE_WALKING_SPEED;
        return (int) Math.ceil(averageWalkingTime);
    }

    private double calculateDistanceToDestination(Location destination) {
        double parkingLongitude = this.location.getLongitude();
        double parkingLatitude = this.location.getLatitude();

        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        double deltaLatitude = Math.abs(parkingLatitude - destination.getLatitude()) * toRadian;
        double deltaLongitude = Math.abs(parkingLongitude - destination.getLongitude()) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(parkingLatitude * toRadian) * Math.cos(destination.getLatitude() * toRadian)
                                * sinDeltaLng
                                * sinDeltaLng);

        return 2 * radius * Math.asin(squareRoot);
    }

    public int calculateUpdatedDiff(LocalDateTime now) {
        Duration diff = Duration.between(now, getUpdatedAt());
        Long diffMinute = diff.getSeconds() / MINUTE_UNIT;
        return diffMinute.intValue();
    }

    public boolean isLocationAvailable() {
        return !location.equals(Location.NO_PROVIDE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parking parking = (Parking) o;
        return Objects.equals(id, parking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
