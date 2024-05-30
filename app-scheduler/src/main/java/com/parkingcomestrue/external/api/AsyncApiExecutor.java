package com.parkingcomestrue.external.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class AsyncApiExecutor {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(100, (Runnable r) -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
    );

    public static <U> CompletableFuture<U> executeAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier::get, executorService);
    }
}
