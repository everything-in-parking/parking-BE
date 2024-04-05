package com.parkingcomestrue.external.parkingapi.seoul;

import com.parkingcomestrue.parking.domain.BaseInformation;
import com.parkingcomestrue.parking.domain.Fee;
import com.parkingcomestrue.parking.domain.FeePolicy;
import com.parkingcomestrue.parking.domain.FreeOperatingTime;
import com.parkingcomestrue.parking.domain.Location;
import com.parkingcomestrue.parking.domain.OperatingTime;
import com.parkingcomestrue.parking.domain.OperationType;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.parking.domain.ParkingType;
import com.parkingcomestrue.parking.domain.PayTypes;
import com.parkingcomestrue.parking.domain.Space;
import com.parkingcomestrue.parking.domain.TimeInfo;
import com.parkingcomestrue.parking.domain.TimeUnit;
import com.parkingcomestrue.external.parkingapi.seoul.SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SeoulPublicParkingAdapter {

    private static final Set<String> TIMED_PARKING_RULES = Set.of("1", "3", "5");
    private static final String STREET_PARKING_TYPE = "노상 주차장";
    private static final String FREE = "무료";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final String HOURS_24 = "2400";

    public List<Parking> convert(SeoulPublicParkingResponse response) {
        List<SeoulCityParking> rows = response.getParkingInfo().getRows();
        List<SeoulCityParking> seoulCityParkingLots = calculateCapacity(filterByOperation(rows));

        return seoulCityParkingLots.stream()
                .map(this::toParking)
                .toList();
    }

    private List<SeoulCityParking> filterByOperation(final List<SeoulCityParking> rows) {
        return rows.stream()
                .filter(result -> TIMED_PARKING_RULES.contains(result.getOperationRule()))
                .toList();
    }

    private List<SeoulCityParking> calculateCapacity(final List<SeoulCityParking> results) {
        final Map<String, List<SeoulCityParking>> collect = results.stream()
                .collect(Collectors.groupingBy(a -> a.getParkingCode()));

        return collect.values().stream().map(parkingLots -> {
            final SeoulCityParking parking = parkingLots.get(0);
            if (parking.getParkingTypeNM().equals(STREET_PARKING_TYPE)) {
                parking.setCapacity(parkingLots.size());
                return parking;
            }
            return parking;
        }).toList();
    }

    private Parking toParking(final SeoulCityParking response) {
        return new Parking(
                getBaseInformation(response),
                getLocation(response),
                getSpace(response),
                getFreeOperatingTime(response),
                getOperatingTime(response),
                getFeePolicy(response)
        );
    }

    private BaseInformation getBaseInformation(final SeoulCityParking response) {
        return new BaseInformation(
                response.getParkingName(),
                response.getTel(),
                response.getAddr(),
                PayTypes.DEFAULT,
                ParkingType.find(response.getParkingTypeNM()),
                OperationType.PUBLIC
        );
    }

    private Location getLocation(final SeoulCityParking response) {
        return Location.of(response.getLng(), response.getLat());
    }

    private Space getSpace(final SeoulCityParking response) {
        return Space.of(
                response.getCapacity(),
                response.getCurParking()
        );
    }

    private FreeOperatingTime getFreeOperatingTime(SeoulCityParking response) {
        if (response.getPayNM().equals(FREE)) {
            return FreeOperatingTime.ALWAYS_FREE;
        }
        return new FreeOperatingTime(
                TimeInfo.CLOSED,
                response.getSaturdayPayNM().equals(FREE) ? TimeInfo.ALL_DAY : TimeInfo.CLOSED,
                response.getHolidayPayNM().equals(FREE) ? TimeInfo.ALL_DAY : TimeInfo.CLOSED
        );
    }

    private OperatingTime getOperatingTime(final SeoulCityParking response) {
        return new OperatingTime(
                new TimeInfo(
                        parsingOperationTime(response.getWeekdayBeginTime()),
                        parsingOperationTime(response.getWeekdayEndTime())
                ),
                new TimeInfo(
                        parsingOperationTime(response.getWeekendBeginTime()),
                        parsingOperationTime(response.getWeekendEndTime())
                ),
                new TimeInfo(
                        parsingOperationTime(response.getHolidayBeginTime()),
                        parsingOperationTime(response.getHolidayEndTime())
                )
        );
    }

    private LocalTime parsingOperationTime(String time) {
        if (time.equals(HOURS_24)) {
            return LocalTime.MAX;
        }
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeException e) {
            return null;
        }
    }

    private FeePolicy getFeePolicy(final SeoulCityParking response) {
        return new FeePolicy(
                Fee.from(response.getRates()),
                Fee.from(response.getAddRates()),
                TimeUnit.from(response.getTimeRate()),
                TimeUnit.from(response.getAddTimeRate()),
                Fee.from(response.getDayMaximum())
        );
    }
}
