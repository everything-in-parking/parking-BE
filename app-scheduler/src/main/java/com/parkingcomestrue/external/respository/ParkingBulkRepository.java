package com.parkingcomestrue.external.respository;

import com.parkingcomestrue.common.domain.parking.Location;
import com.parkingcomestrue.common.domain.parking.Parking;
import java.sql.PreparedStatement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class ParkingBulkRepository {

    private final ParameterizedPreparedStatementSetter<Parking> PARKING_PARAMETERIZED_PREPARED_STATEMENT_SETTER = (PreparedStatement ps, Parking parking) -> {
        ps.setInt(1, parking.getFeePolicy().getBaseFee().getFee());
        ps.setInt(2, parking.getFeePolicy().getBaseTimeUnit().getTimeUnit());
        ps.setInt(3, parking.getFeePolicy().getExtraFee().getFee());
        ps.setInt(4, parking.getFeePolicy().getExtraTimeUnit().getTimeUnit());
        ps.setInt(5, parking.getFeePolicy().getDayMaximumFee().getFee());

        ps.setInt(6, parking.getSpace().getCapacity());
        ps.setInt(7, parking.getSpace().getCurrentParking());

        ps.setObject(8, parking.getOperatingTime().getHolidayBeginTime());
        ps.setObject(9, parking.getOperatingTime().getHolidayEndTime());
        ps.setObject(10, parking.getFreeOperatingTime().getHolidayBeginTime());
        ps.setObject(11, parking.getFreeOperatingTime().getHolidayEndTime());

        ps.setObject(12, parking.getOperatingTime().getSaturdayBeginTime());
        ps.setObject(13, parking.getOperatingTime().getSaturdayEndTime());
        ps.setObject(14, parking.getFreeOperatingTime().getSaturdayBeginTime());
        ps.setObject(15, parking.getFreeOperatingTime().getSaturdayEndTime());

        ps.setObject(16, parking.getOperatingTime().getWeekdayBeginTime());
        ps.setObject(17, parking.getOperatingTime().getWeekdayEndTime());
        ps.setObject(18, parking.getFreeOperatingTime().getWeekdayBeginTime());
        ps.setObject(19, parking.getFreeOperatingTime().getWeekdayEndTime());

        ps.setObject(20, parking.getCreatedAt());
        ps.setObject(21, parking.getUpdatedAt());

        ps.setString(22, parking.getBaseInformation().getAddress());
        ps.setString(23, parking.getBaseInformation().getName());
        ps.setString(24, parking.getBaseInformation().getTel());
        ps.setString(25, parking.getBaseInformation().getOperationType().name());
        ps.setString(26, parking.getBaseInformation().getParkingType().name());
        ps.setString(27, parking.getBaseInformation().getPayTypesName());
        ps.setString(28, toWKT(parking.getLocation()));
    };

    private String toWKT(Location location) {
        return "POINT(" + location.getLatitude() + " " + location.getLongitude() + ")";
    }

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAllWithBulk(List<Parking> parkingLots) {
        String sql = "INSERT INTO parking "
                + "(base_fee, base_time_unit, extra_fee, extra_time_unit, day_maximum_fee, "
                + "capacity, current_parking, "
                + "holiday_begin_time, holiday_end_time, holiday_free_begin_time, holiday_free_end_time, "
                + "saturday_begin_time, saturday_end_time, saturday_free_begin_time, saturday_free_end_time, "
                + "weekday_begin_time, weekday_end_time, weekday_free_begin_time, weekday_free_end_time, "
                + "created_at, updated_at, "
                + "address, name, tel, operation_type, parking_type, pay_types, location) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ST_GeomFromText(?))";

        jdbcTemplate.batchUpdate(
                sql,
                parkingLots,
                parkingLots.size(),
                PARKING_PARAMETERIZED_PREPARED_STATEMENT_SETTER
        );
    }
}
