package com.example.parking.application.review.dto;

import java.util.List;

public record ReviewInfoResponse(int totalReviewCount, List<ReviewCountResponse> reviews) {

}
