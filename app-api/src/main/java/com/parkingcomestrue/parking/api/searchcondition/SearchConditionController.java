package com.parkingcomestrue.parking.api.searchcondition;

import com.parkingcomestrue.parking.application.member.dto.MemberId;
import com.parkingcomestrue.parking.application.searchcondition.SearchConditionService;
import com.parkingcomestrue.parking.application.searchcondition.dto.SearchConditionDto;
import com.parkingcomestrue.parking.config.argumentresolver.MemberAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "조회 조건 컨트롤러")
@RequiredArgsConstructor
@RestController
public class SearchConditionController {

    private final SearchConditionService searchConditionService;

    @Operation(summary = "조회 조건 조회", description = "조회 조건 조회")
    @GetMapping("/search-condition")
    public ResponseEntity<SearchConditionDto> loadSearchCondition(
            @Parameter(hidden = true) @MemberAuth MemberId memberId) {
        return ResponseEntity.ok(searchConditionService.findSearchCondition(memberId));
    }

    @Operation(summary = "조회 조건 수정", description = "조회 조건 수정")
    @PutMapping("/search-condition")
    public ResponseEntity<Void> updateSearchCondition(@Parameter(hidden = true) @MemberAuth MemberId memberId,
                                                      SearchConditionDto request) {
        searchConditionService.updateSearchCondition(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
