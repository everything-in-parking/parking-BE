package com.example.parking.external.parkingapi.pusan;

import static com.example.parking.external.parkingapi.pusan.PusanPublicParkingResponse.ParkingInfo.*;

import com.example.parking.domain.parking.BaseInformation;
import com.example.parking.domain.parking.Fee;
import com.example.parking.domain.parking.FeePolicy;
import com.example.parking.domain.parking.FreeOperatingTime;
import com.example.parking.domain.parking.Location;
import com.example.parking.domain.parking.OperatingTime;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayTypes;
import com.example.parking.domain.parking.Space;
import com.example.parking.domain.parking.TimeInfo;
import com.example.parking.domain.parking.TimeUnit;
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
