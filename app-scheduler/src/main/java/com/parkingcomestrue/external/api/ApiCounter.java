package com.parkingcomestrue.external.api;

import java.util.concurrent.atomic.AtomicInteger;

public class ApiCounter {

    private final int MIN_TOTAL_COUNT;

    private AtomicInteger totalCount;
    private AtomicInteger errorCount;
    private boolean isOpened;

    public ApiCounter() {
        this.MIN_TOTAL_COUNT = 10;
        this.totalCount = new AtomicInteger(0);
        this.errorCount = new AtomicInteger(0);
        this.isOpened = false;
    }

    public ApiCounter(int minTotalCount) {
        this.MIN_TOTAL_COUNT = minTotalCount;
        this.totalCount = new AtomicInteger(0);
        this.errorCount = new AtomicInteger(0);
        this.isOpened = false;
    }

    public void totalCountUp() {
        totalCount.incrementAndGet();
    }

    public void errorCountUp() {
        totalCountUp();
        errorCount.incrementAndGet();
    }

    public void reset() {
        totalCount = new AtomicInteger(0);
        errorCount = new AtomicInteger(0);
        isOpened = false;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void open() {
        isOpened = true;
    }

    public boolean isErrorRateOverThan(double errorRate) {
        int currentTotalCount = getTotalCount();
        int currentErrorCount = getErrorCount();
        if (currentTotalCount < MIN_TOTAL_COUNT) {
            return false;
        }
        double currentErrorRate = (double) currentErrorCount / currentTotalCount;
        return currentErrorRate >= errorRate;
    }

    public int getTotalCount() {
        return totalCount.get();
    }
    public int getErrorCount() { return errorCount.get(); }
}
