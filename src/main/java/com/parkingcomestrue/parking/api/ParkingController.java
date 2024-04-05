package com.parkingcomestrue.parking.api;

import com.parkingcomestrue.parking.application.ParkingService;
import com.parkingcomestrue.parking.application.dto.ParkingDetailInfoResponse;
import com.parkingcomestrue.parking.application.dto.ParkingLotsResponse;
import com.parkingcomestrue.parking.application.dto.ParkingQueryRequest;
import com.parkingcomestrue.parking.application.dto.ParkingSearchConditionRequest;
import com.parkingcomestrue.config.argumentresolver.MemberAuth;
import com.parkingcomestrue.config.argumentresolver.parking.ParkingQuery;
import com.parkingcomestrue.config.argumentresolver.parking.ParkingSearchCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주차장 컨트롤러")
@RequiredArgsConstructor
@RestController
public class ParkingController {

    private final ParkingService parkingService;

    @Operation(summary = "주차장 상세조회", description = "주차장 상세조회")
    @GetMapping("/parkings/{parkingId}")
    public ResponseEntity<ParkingDetailInfoResponse> findParking(@PathVariable Long parkingId) {
        ParkingDetailInfoResponse parkingDetailInfoResponse = parkingService.findParking(parkingId);
        return ResponseEntity.status(HttpStatus.OK).body(parkingDetailInfoResponse);

    }

    @Operation(summary = "주차장 반경 조회", description = "주차장 반경 조회")
    @GetMapping("/parkings")
    public ResponseEntity<ParkingLotsResponse> find(
            @ParkingQuery ParkingQueryRequest parkingQueryRequest,
            @ParkingSearchCondition ParkingSearchConditionRequest parkingSearchConditionRequest,
            @Parameter(hidden = true) @MemberAuth(nullable = true) Long parkingMemberId
    ) {
        ParkingLotsResponse parkingLots = parkingService.findParkingLots(parkingQueryRequest,
                parkingSearchConditionRequest, parkingMemberId);
        return ResponseEntity.ok(parkingLots);
    }
}
