package com.parkingcomestrue.external.parkingapi.seoul;

import com.parkingcomestrue.common.domain.parking.BaseInformation;
import com.parkingcomestrue.common.domain.parking.Fee;
import com.parkingcomestrue.common.domain.parking.FeePolicy;
import com.parkingcomestrue.common.domain.parking.FreeOperatingTime;
import com.parkingcomestrue.common.domain.parking.Location;
import com.parkingcomestrue.common.domain.parking.OperatingTime;
import com.parkingcomestrue.common.domain.parking.OperationType;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.domain.parking.ParkingType;
import com.parkingcomestrue.common.domain.parking.PayType;
import com.parkingcomestrue.common.domain.parking.Space;
import com.parkingcomestrue.common.domain.parking.TimeInfo;
import com.parkingcomestrue.common.domain.parking.TimeUnit;
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
        List<SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking> rows = response.getParkingInfo().getRows();
        List<SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking> seoulCityParkingLots = calculateCapacity(
                filterByOperation(rows));

        return seoulCityParkingLots.stream()
                .map(this::toParking)
                .toList();
    }

    private List<SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking> filterByOperation(
            final List<SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking> rows) {
        return rows.stream()
                .filter(result -> TIMED_PARKING_RULES.contains(result.getOperationRule()))
                .toList();
    }

    private List<SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking> calculateCapacity(
            final List<SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking> results) {
        final Map<String, List<SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking>> collect = results.stream()
                .collect(Collectors.groupingBy(a -> a.getParkingCode()));

        return collect.values().stream().map(parkingLots -> {
            final SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking parking = parkingLots.get(0);
            if (parking.getParkingTypeNM().equals(STREET_PARKING_TYPE)) {
                parking.setCapacity(parkingLots.size());
                return parking;
            }
            return parking;
        }).toList();
    }

    private Parking toParking(final SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking response) {
        return new Parking(
                getBaseInformation(response),
                getLocation(response),
                getSpace(response),
                getFreeOperatingTime(response),
                getOperatingTime(response),
                getFeePolicy(response)
        );
    }

    private BaseInformation getBaseInformation(final SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking response) {
        return new BaseInformation(
                response.getParkingName(),
                response.getTel(),
                response.getAddr(),
                List.of(PayType.NO_INFO),
                ParkingType.find(response.getParkingTypeNM()),
                OperationType.PUBLIC
        );
    }

    private Location getLocation(final SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking response) {
        return Location.of(response.getLng(), response.getLat());
    }

    private Space getSpace(final SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking response) {
        return Space.of(
                response.getCapacity(),
                response.getCurParking()
        );
    }

    private FreeOperatingTime getFreeOperatingTime(SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking response) {
        if (response.getPayNM().equals(FREE)) {
            return FreeOperatingTime.ALWAYS_FREE;
        }
        return new FreeOperatingTime(
                TimeInfo.CLOSED,
                response.getSaturdayPayNM().equals(FREE) ? TimeInfo.ALL_DAY : TimeInfo.CLOSED,
                response.getHolidayPayNM().equals(FREE) ? TimeInfo.ALL_DAY : TimeInfo.CLOSED
        );
    }

    private OperatingTime getOperatingTime(final SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking response) {
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

    private FeePolicy getFeePolicy(final SeoulPublicParkingResponse.ParkingInfo.SeoulCityParking response) {
        return new FeePolicy(
                Fee.from(response.getRates()),
                Fee.from(response.getAddRates()),
                TimeUnit.from(response.getTimeRate()),
                TimeUnit.from(response.getAddTimeRate()),
                Fee.from(response.getDayMaximum())
        );
    }
}
