package com.parkingcomestrue.external.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncApiExecutorConfig {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(100, (Runnable r) -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
    }
}
