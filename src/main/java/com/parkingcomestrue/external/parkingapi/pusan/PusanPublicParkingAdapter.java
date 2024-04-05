package com.parkingcomestrue.external.parkingapi.pusan;

import static com.parkingcomestrue.external.parkingapi.pusan.PusanPublicParkingResponse.ParkingInfo.*;

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
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PusanPublicParkingAdapter {

    private static final String EMPTY = "-";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String HOURS_24 = "24:00";

    public List<Parking> convert(PusanPublicParkingResponse response) {
        return response.getGetParkingInfoDetails().getBody().getItems().getItem()
                .stream().map(this::toParking)
                .toList();
    }

    private Parking toParking(final Item response) {
        return new Parking(
                getBaseInformation(response),
                getLocation(response),
                getSpace(response),
                getFreeOperatingTime(response),
                getOperatingTime(response),
                getFeePolicy(response)
        );
    }

    private BaseInformation getBaseInformation(final Item response) {
        return new BaseInformation(
                response.getParkingName(),
                response.getTelephoneNumber(),
                filterAddress(response),
                PayTypes.DEFAULT,
                ParkingType.find(response.getParkingTypeNM()),
                OperationType.PUBLIC
        );
    }

    private String filterAddress(Item response) {
        if (response.getOldAddress().equals(EMPTY)) {
            return response.getNewAddress();
        }
        return response.getOldAddress();
    }

    private Location getLocation(final Item response) {
        return Location.of(response.getLongitude(), response.getLatitude());
    }

    private Space getSpace(final Item response) {
        return Space.of(response.getCapacity(), response.getCurParking());
    }

    private OperatingTime getOperatingTime(final Item response) {
        return new OperatingTime(
                new TimeInfo(parsingOperationTime(response.getWeekdayBeginTime()),
                        parsingOperationTime(response.getWeekdayEndTime())),
                new TimeInfo(parsingOperationTime(response.getWeekendBeginTime()),
                        parsingOperationTime(response.getWeekendEndTime())),
                new TimeInfo(parsingOperationTime(response.getHolidayBeginTime()),
                        parsingOperationTime(response.getHolidayEndTime()))
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

    private FeePolicy getFeePolicy(final Item response) {
        return new FeePolicy(
                Fee.from(response.getRates()),
                Fee.from(response.getAddRates()),
                TimeUnit.from(response.getTimeRate()),
                TimeUnit.from(response.getAddTimeRate()),
                Fee.from(response.getDayMaximum())
        );
    }

    private FreeOperatingTime getFreeOperatingTime(final Item response) {
        if (response.getRates().equals("0") && response.getAddRates().equals("0")) {
            return FreeOperatingTime.ALWAYS_FREE;
        }
        return FreeOperatingTime.ALWAYS_PAY;
    }
}
