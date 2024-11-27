package com.parkingcomestrue.external.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class ApiCounterTest {

    @Test
    void 전체_요청이_최소_횟수를_넘고_예외가_특정_지수를_넘어가면_true를_반환한다() {
        //given
        ApiCounter apiCounter = new ApiCounter();

        for (int i = 0; i < 8; i++) {
            apiCounter.totalCountUp();
        }
        for (int i = 0; i < 2; i++) {
            apiCounter.errorCountUp();
        }

        //when
        boolean actual = apiCounter.isErrorRateOverThan(0.2);

        //then
        assertThat(actual).isTrue();
    }

    @Test
    void 여러_스레드에서도_전체_요청이_최소_횟수를_넘고_예외가_특정_지수를_넘어가면_true를_반환한다() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        ApiCounter apiCounter = new ApiCounter();
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);

        //when
        for (int i = 0; i < threadCount; i++) {
            if (i % 10 == 0 || i % 10 == 1) {
                executorService.submit(() -> {
                    try {
                        apiCounter.errorCountUp();
                    } finally {
                        latch.countDown();
                    }
                });
                continue;
            }
            executorService.submit(() -> {
                try {
                    apiCounter.totalCountUp();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        boolean actual = apiCounter.isErrorRateOverThan(0.2);

        //then
        assertThat(actual).isTrue();
    }

    @Test
    void 여러_스레드에서_카운트를_증가시킬수있다() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        ApiCounter apiCounter = new ApiCounter();
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);

        //when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    apiCounter.totalCountUp();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //then
        assertThat(apiCounter.getTotalCount()).isEqualTo(threadCount);
    }
}
