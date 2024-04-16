package com.example.parking.api.parking;

import com.example.parking.application.parking.ParkingService;
import com.example.parking.application.parking.dto.ParkingDetailInfoResponse;
import com.example.parking.application.parking.dto.ParkingLotsResponse;
import com.example.parking.application.parking.dto.ParkingQueryRequest;
import com.example.parking.application.parking.dto.ParkingSearchConditionRequest;
import com.example.parking.config.argumentresolver.MemberAuth;
import com.example.parking.config.argumentresolver.parking.ParkingQuery;
import com.example.parking.config.argumentresolver.parking.ParkingSearchCondition;
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
