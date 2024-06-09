package com.parkingcomestrue.external.api;

import com.parkingcomestrue.external.api.parkingapi.HealthCheckResponse;

public interface HealthChecker {

    HealthCheckResponse check();
}
