package com.parkingcomestrue.parking.application.favorite.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteCreateRequest {

    private Long parkingId;

    public FavoriteCreateRequest(Long parkingId) {
        this.parkingId = parkingId;
    }
}
