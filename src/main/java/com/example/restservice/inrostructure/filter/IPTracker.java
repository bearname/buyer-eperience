package com.example.restservice.inrostructure.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class IPTracker {

    private final String ipAddress;
    private final CopyOnWriteArrayList<Long> timeStamps = new CopyOnWriteArrayList<>();
    private Long bandUntilTime;
    private final int bandTimeInMs;

    public IPTracker(String ipAddress, long startCurrentTimeMillis, int bandTimeInMs) {
        this.ipAddress = ipAddress;
        timeStamps.add(startCurrentTimeMillis);
        bandUntilTime = startCurrentTimeMillis - 1000;
        this.bandTimeInMs = bandTimeInMs;
    }


    public void addEntry(int maxRequestsPerTimePeriod,
                         int timePeriodInMs,
                         long currentTimeMillis) {
        cleanEntries(timePeriodInMs, currentTimeMillis);
        addEntry(currentTimeMillis);
    }


    public void cleanEntries(int timePeriodInMs,
                             long currentTimeMillis) {
        List<Long> tempList = findTimesToRemove(timePeriodInMs, currentTimeMillis);
        timeStamps.removeAll(tempList);
    }


    public void addEntry(long currentTimeMillis) {
        timeStamps.add(currentTimeMillis);
    }

    public boolean hasReachedRateLimit(int maxRequestsPerTimePeriod,
                                       int timePeriodInMs,
                                       long currentTimeMillis) {
        boolean result = false;
        if (currentTimeMillis < bandUntilTime) {
            return true;
        }
        addEntry(maxRequestsPerTimePeriod, timePeriodInMs, currentTimeMillis);
        if (timeStamps.size() >= maxRequestsPerTimePeriod) {
            long bt = Integer.valueOf(bandTimeInMs).longValue();
            bandUntilTime = (currentTimeMillis + bt);
            result = true;
        }
        return result;
    }

    public int size() {
        return timeStamps.size();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public List<Long> getTimeStamps() {
        return timeStamps;
    }

    private List<Long> findTimesToRemove(int timePeriodInMs, long currentTimeMillis) {
        List<Long> tempList = new ArrayList<>();
        for (Long timestamp : timeStamps) {
            if (timestamp <= (currentTimeMillis - timePeriodInMs)) {
                tempList.add(timestamp);
            }
        }
        return tempList;
    }
}