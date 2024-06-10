package com.parkingcomestrue.external.api;

import lombok.Getter;

@Getter
public class HealthCheckResponse {

    boolean isHealthy;
    int totalSize;

    public HealthCheckResponse(boolean isHealthy, int totalSize) {
        this.isHealthy = isHealthy;
        this.totalSize = totalSize;
    }
}
