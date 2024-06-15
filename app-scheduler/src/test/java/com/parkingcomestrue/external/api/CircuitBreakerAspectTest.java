package com.parkingcomestrue.external.api;

import com.parkingcomestrue.fake.CircuitBreakerTestService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CircuitBreakerAspectTest {

    /**
     * 요청 중 20%의 예외가 발생하면 api 요청 잠김
     * 잠긴 후, 200ms 후에 다시 요청보내지도록 reset
     */
    @Autowired
    private CircuitBreakerTestService service;

    private boolean[] isExecuted = {false, false};

    @Test
    void 서비스에_에러가_특정_지수를_넘으면_요청이_잠긴다() {
        //given
        for (int i = 0; i < 8; i++) {
            service.call(() -> {});
        }
        for (int i = 0; i < 2; i++) {
            service.call(() -> {throw new RuntimeException();});
        }

        //when
        service.call(() -> isExecuted[0] = true);

        //then
        Assertions.assertThat(isExecuted[0]).isFalse();
    }

    @Test
    void 서비스가_잠긴후_특정시간이_지나면_다시_요청을_보낼수있다() throws InterruptedException {
        //given
        for (int i = 0; i < 8; i++) {
            service.call(() -> {});
        }
        for (int i = 0; i < 2; i++) {
            service.call(() -> {throw new RuntimeException();});
        }
        Thread.sleep(1000);

        //when
        service.call(() -> isExecuted[1] = true);

        //then
        Assertions.assertThat(isExecuted[1]).isTrue();
    }
}
