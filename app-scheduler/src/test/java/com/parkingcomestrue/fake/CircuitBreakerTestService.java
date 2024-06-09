package com.parkingcomestrue.fake;

import com.parkingcomestrue.external.api.CircuitBreaker;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CircuitBreakerTestService {

    @CircuitBreaker(resetTime = 2, timeUnit = TimeUnit.SECONDS)
    public void call(Runnable runnable) {
        runnable.run();
    }
}
