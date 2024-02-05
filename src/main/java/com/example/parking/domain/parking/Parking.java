package com.example.parking.domain.parking;

import static lombok.AccessLevel.PROTECTED;

import com.example.parking.domain.AuditingEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
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
}
