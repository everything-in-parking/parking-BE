package com.parkingcomestrue.external.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class AsyncApiExecutorConfigTest {

    private static final int MINUTE = 1000;

    private final ExecutorService executorService = Executors.newFixedThreadPool(100, (Runnable r) -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    @Test
    void executeAsync_메서드를_사용하면_100개의_스레드로_비동기_동작한다() {
        //given
        int pageNumber = 1;
        int lastPageNumber = 100;

        //when
        long start = System.currentTimeMillis();
        List<CompletableFuture<Integer>> testCalls = Stream.iterate(pageNumber, i -> i <= lastPageNumber, i -> i + 1)
                .map(i -> CompletableFuture.supplyAsync(() -> testCall(i), executorService))
                .toList();
        long end = System.currentTimeMillis();

        Integer sum = CompletableFuture.allOf(testCalls.toArray(new CompletableFuture[0]))
                .thenApply(Void -> testCalls.stream()
                        .mapToInt(CompletableFuture::join)
                        .sum())
                .join();

        Integer expected = lastPageNumber * (lastPageNumber + 1) / 2; // 가우스 합

        //then
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat((end - start)).isLessThanOrEqualTo(100 * MINUTE);
            soft.assertThat(sum).isEqualTo(expected);
        });
    }

    private Integer testCall(Integer pageNumber) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pageNumber;
    }
}
