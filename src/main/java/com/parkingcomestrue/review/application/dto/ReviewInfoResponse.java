package com.parkingcomestrue.review.application.dto;

import java.util.List;

public record ReviewInfoResponse(int totalReviewCount, List<ReviewCountResponse> reviews) {

}
