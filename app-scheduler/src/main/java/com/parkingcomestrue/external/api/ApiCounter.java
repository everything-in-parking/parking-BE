package com.parkingcomestrue.external.api;

public class ApiCounter {

    private final double MIN_TOTAL_COUNT;

    private double totalCount;
    private double errorCount;
    private boolean isClosed;

    public ApiCounter() {
        this.MIN_TOTAL_COUNT = 10;
        this.totalCount = 0;
        this.errorCount = 0;
        this.isClosed = false;
    }

    public ApiCounter(double minTotalCount) {
        this.MIN_TOTAL_COUNT = minTotalCount;
        this.totalCount = 0;
        this.errorCount = 0;
        this.isClosed = false;
    }

    public synchronized void countUp() {
        totalCount++;
    }

    public synchronized void errorCountUp() {
        totalCount++;
        errorCount++;
    }

    public void reset() {
        totalCount = 0;
        errorCount = 0;
        isClosed = false;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void close() {
        isClosed = true;
    }

    public boolean isErrorRateOverThan(double errorRate) {
        if (totalCount < MIN_TOTAL_COUNT) {
            return false;
        }
        double currentErrorRate = errorCount / totalCount;
        return currentErrorRate >= errorRate;
    }

    public double getTotalCount() {
        return totalCount;
    }
}
