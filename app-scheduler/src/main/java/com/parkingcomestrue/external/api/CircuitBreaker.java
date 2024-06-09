package com.parkingcomestrue.external.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CircuitBreaker {

    int minTotalCount() default 10;
    double errorRate() default 0.2;
    long resetTime() default 30;
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
