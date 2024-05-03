package com.parkingcomestrue.external.parkingapi.korea;

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
import com.parkingcomestrue.common.domain.parking.PayTypes;
import com.parkingcomestrue.common.domain.parking.Space;
import com.parkingcomestrue.common.domain.parking.TimeInfo;
import com.parkingcomestrue.common.domain.parking.TimeUnit;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class KoreaParkingAdapter {

    private static final String NO_PROVIDE = "-1";
    private static final String FREE = "무료";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String HOURS_24 = "23:59";
    private static final String HOURS_00 = "00:00";

    public List<Parking> convert(KoreaParkingResponse response) {
        return response.getResponse().getBody().getItems().stream()
                .map(this::toParking)
                .toList();
    }

    private Parking toParking(KoreaParkingResponse.Response.Body.Item item) {
        return new Parking(
                getBaseInformation(item),
                getLocation(item),
                getSpace(item),
                getFreeOperatingTime(item),
                getOperatingTime(item),
                getFeePolicy(item)
        );
    }

    private BaseInformation getBaseInformation(KoreaParkingResponse.Response.Body.Item item) {
        return new BaseInformation(
                item.getParkingName(),
                item.getTel(),
                filterAddress(item),
                toPayTypes(item),
                ParkingType.find(item.getParkingType()),
                OperationType.find(item.getOperationType())
        );
    }

    private String filterAddress(KoreaParkingResponse.Response.Body.Item item) {
        if (Strings.isBlank(item.getOldAddress())) {
            return item.getNewAddress();
        }
        return item.getOldAddress();
    }

    private PayTypes toPayTypes(KoreaParkingResponse.Response.Body.Item item) {
        List<PayType> payTypes = new ArrayList<>();
        for (PayType payType : PayType.values()) {
            if (item.getPayType().contains(payType.getDescription())) {
                payTypes.add(payType);
            }
        }
        return PayTypes.from(payTypes);
    }

    private Location getLocation(KoreaParkingResponse.Response.Body.Item item) {
        return Location.of(item.getLongitude(), item.getLatitude());
    }

    private Space getSpace(KoreaParkingResponse.Response.Body.Item item) {
        return Space.of(item.getCapacity(), NO_PROVIDE);
    }

    private FreeOperatingTime getFreeOperatingTime(KoreaParkingResponse.Response.Body.Item item) {
        if (item.getFeeInfo().equals(FREE)) {
            return FreeOperatingTime.ALWAYS_FREE;
        }
        return FreeOperatingTime.ALWAYS_PAY;
    }

    private OperatingTime getOperatingTime(KoreaParkingResponse.Response.Body.Item item) {
        return new OperatingTime(
                toTimeInfo(item.getWeekDayBeginTime(), item.getWeekdayEndTime()),
                toTimeInfo(item.getSaturdayBeginTime(), item.getSaturdayEndTime()),
                toTimeInfo(item.getHolidayBeginTime(), item.getHolidayEndTime())
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
            return LocalTime.MAX;
        }
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeException e) {
            return null;
        }
    }

    private FeePolicy getFeePolicy(KoreaParkingResponse.Response.Body.Item item) {
        return new FeePolicy(
                Fee.from(item.getBaseFee()),
                Fee.from(item.getExtraFee()),
                TimeUnit.from(item.getBaseTimeUnit()),
                TimeUnit.from(item.getExtraTimeUnit()),
                Fee.from(item.getDayMaximumFee())
        );
    }
}
