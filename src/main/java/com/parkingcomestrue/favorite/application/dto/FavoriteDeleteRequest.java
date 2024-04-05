package com.parkingcomestrue.favorite.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteDeleteRequest {

    private Long parkingId;

    public FavoriteDeleteRequest(Long parkingId) {
        this.parkingId = parkingId;
    }
}
