package com.parkingcomestrue.parking.api.review;

import com.parkingcomestrue.parking.application.review.ReviewService;
import com.parkingcomestrue.parking.application.review.dto.ReviewCreateRequest;
import com.parkingcomestrue.parking.config.argumentresolver.MemberAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰 컨트롤러")
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "리뷰 등록")
    @PostMapping("/parkings/{parkingId}/reviews")
    public ResponseEntity<Long> createReview(@PathVariable Long parkingId,
                                             @Parameter(hidden = true) @MemberAuth Long memberId,
                                             @ModelAttribute ReviewCreateRequest request) {
        Long reviewId = reviewService.createReview(parkingId, memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewId);
    }
}
