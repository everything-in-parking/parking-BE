package com.example.parking.api.searchcondition;

import com.example.parking.application.searchcondition.SearchConditionService;
import com.example.parking.application.searchcondition.dto.SearchConditionDto;
import com.example.parking.external.config.argumentresolver.MemberAuth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchConditionController {

    private final SearchConditionService searchConditionService;

    public SearchConditionController(SearchConditionService searchConditionService) {
        this.searchConditionService = searchConditionService;
    }

    @GetMapping("/users/parkings/options")
    public ResponseEntity<SearchConditionDto> loadSearchCondition(@MemberAuth Long memberId) {
        return ResponseEntity.ok(searchConditionService.findSearchCondition(memberId));
    }

    @PutMapping("/users/parkings/options")
    public ResponseEntity<Void> updateSearchCondition(@MemberAuth Long memberId, SearchConditionDto request) {
        searchConditionService.updateSearchCondition(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
