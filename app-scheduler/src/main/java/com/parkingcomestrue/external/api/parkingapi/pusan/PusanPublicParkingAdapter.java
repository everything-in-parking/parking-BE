package com.parkingcomestrue.external.api.parkingapi.pusan;

import static com.parkingcomestrue.common.domain.parking.TimeInfo.MAX_END_TIME;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class PusanPublicParkingAdapter {

    private static final String EMPTY = "-";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String HOURS_24 = "24:00";
    private static final String HOURS_00 = "00:00";

    public List<Parking> convert(PusanPublicParkingResponse response) {
        return response.getGetParkingInfoDetails().getBody().getItems().getItem()
                .stream().map(this::toParking)
                .toList();
    }

    private Parking toParking(final PusanPublicParkingResponse.ParkingInfo.Item response) {
        return new Parking(
                getBaseInformation(response),
                getLocation(response),
                getSpace(response),
                getFreeOperatingTime(response),
                getOperatingTime(response),
                getFeePolicy(response)
        );
    }

    private BaseInformation getBaseInformation(final PusanPublicParkingResponse.ParkingInfo.Item response) {
        return new BaseInformation(
                response.getParkingName(),
                response.getTelephoneNumber(),
                filterAddress(response),
                toPayTypes(response),
                ParkingType.find(response.getParkingTypeNM()),
                OperationType.PUBLIC
        );
    }

    private String filterAddress(PusanPublicParkingResponse.ParkingInfo.Item response) {
        if (response.getOldAddress().equals(EMPTY)) {
            return response.getNewAddress();
        }
        return response.getOldAddress();
    }

    private Set<PayType> toPayTypes(PusanPublicParkingResponse.ParkingInfo.Item item) {
        Set<PayType> payTypes = new HashSet<>();
        for (PayType payType : PayType.values()) {
            if (item.getPayType().contains(payType.getDescription())) {
                payTypes.add(payType);
            }
        }
        if (payTypes.isEmpty()) {
            return Set.of(PayType.NO_INFO);
        }
        return payTypes;
    }

    private Location getLocation(final PusanPublicParkingResponse.ParkingInfo.Item response) {
        return Location.of(response.getLongitude(), response.getLatitude());
    }

    private Space getSpace(final PusanPublicParkingResponse.ParkingInfo.Item response) {
        return Space.of(response.getCapacity(), response.getCurParking());
    }

    private OperatingTime getOperatingTime(final PusanPublicParkingResponse.ParkingInfo.Item response) {
        return new OperatingTime(
                toTimeInfo(response.getWeekdayBeginTime(), response.getWeekdayEndTime()),
                toTimeInfo(response.getWeekendBeginTime(), response.getWeekendEndTime()),
                toTimeInfo(response.getHolidayBeginTime(), response.getHolidayEndTime())
        );
    }

    private TimeInfo toTimeInfo(String beginTime, String endTime) {
        if (HOURS_00.equals(beginTime) && HOURS_24.equals(endTime)) {
            return TimeInfo.ALL_DAY;
        }
        if (HOURS_00.equals(beginTime) && HOURS_00.equals(endTime)) {
            return TimeInfo.CLOSED;
        }
        return new TimeInfo(parsingOperationTime(beginTime), parsingOperationTime(endTime));
    }

    private LocalTime parsingOperationTime(String time) {
        if (time.equals(HOURS_24)) {
            return MAX_END_TIME;
        }
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeException e) {
            return null;
        }
    }

    private FeePolicy getFeePolicy(final PusanPublicParkingResponse.ParkingInfo.Item response) {
        return new FeePolicy(
                Fee.from(response.getRates()),
                Fee.from(response.getAddRates()),
                TimeUnit.from(response.getTimeRate()),
                TimeUnit.from(response.getAddTimeRate()),
                Fee.from(response.getDayMaximum())
        );
    }

    private FreeOperatingTime getFreeOperatingTime(final PusanPublicParkingResponse.ParkingInfo.Item response) {
        if (response.getRates().equals("0") && response.getAddRates().equals("0")) {
            return FreeOperatingTime.ALWAYS_FREE;
        }
        return FreeOperatingTime.ALWAYS_PAY;
    }
}
