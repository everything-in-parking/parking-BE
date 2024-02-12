package com.example.parking.domain.parking;

import com.example.parking.domain.AuditingEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parking extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BaseInformation baseInformation;

    @Embedded
    private Location location;

    @Embedded
    private Space space;

    @Embedded
    private FreeOperatingTime freeOperatingTime;

    @Embedded
    private OperatingTime operatingTime;

    @Embedded
    private FeePolicy feePolicy;

    public Parking(BaseInformation baseInformation, Location location, Space space,
                   FreeOperatingTime freeOperatingTime, OperatingTime operatingTime, FeePolicy feePolicy) {
        this.baseInformation = baseInformation;
        this.location = location;
        this.space = space;
        this.freeOperatingTime = freeOperatingTime;
        this.operatingTime = operatingTime;
        this.feePolicy = feePolicy;
    }

    public Fee calculateParkingFee(List<DayParking> dayParkings) {
        return dayParkings.stream()
//                .filter(freePolicy::isNotFreeDay)
                .map(DayParking::getMinutes)
                .map(minutes -> feePolicy.calculateFee(minutes))
                .reduce(Fee::plus)
                .orElse(Fee.ZERO);
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
}
