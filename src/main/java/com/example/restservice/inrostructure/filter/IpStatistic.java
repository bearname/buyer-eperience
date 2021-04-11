package com.example.restservice.inrostructure.filter;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class IpStatistic {

    private final ConcurrentHashMap<String, IPTracker> ipToIPTracker = new ConcurrentHashMap<>();
    private final int maxRequestsPerTimePeriod;
    private final int timePeriodInMs;
    private final int bandTimeInMs;

    public IpStatistic(int maxRequestsPerTimePeriod, int timePeriodInMs, int bandTimeInMs)
    {
        this.maxRequestsPerTimePeriod = maxRequestsPerTimePeriod;
        this.timePeriodInMs = timePeriodInMs;
        this.bandTimeInMs = bandTimeInMs;
    }
    public boolean shouldRateLimit(String ipAddress, long currentTimeMillis)
    {
        boolean result = false;

        if (ipToIPTracker.containsKey(ipAddress))
        {
            IPTracker ipt = ipToIPTracker.get(ipAddress);
            if ((ipt != null) && (ipt.hasReachedRateLimit(maxRequestsPerTimePeriod, timePeriodInMs, currentTimeMillis)))
            {
                result = true;
            }
        }
        else
        {
            IPTracker ipTracker = new IPTracker(ipAddress, currentTimeMillis, bandTimeInMs);
            ipToIPTracker.put(ipAddress, ipTracker);
        }
        return result;
    }

    public IPTracker getIPTracker(String ipAddress) {
        return ipToIPTracker.get(ipAddress);
    }

    public void clean() {
        Date d = new Date();
        clean(d.getTime());
    }

    public void clean(long currentTimeMillis) {
        List<String> keysToRemove = new ArrayList<>();
        for (Map.Entry<String, IPTracker> entry : this.ipToIPTracker.entrySet()) {
            String key = entry.getKey();
            IPTracker ipt = entry.getValue();
            if (ipt != null) {
                ipt.cleanEntries(timePeriodInMs, currentTimeMillis);
                if (ipt.size() == 0) {
                    keysToRemove.add(key);
                }
            }
            else {
                keysToRemove.add(entry.getKey());
            }
        }

        removeIPs(keysToRemove);
    }

    protected void removeIPs( List<String> ipsToRemove) {
        for (String ip : ipsToRemove) {
            if (ip != null) {
                this.ipToIPTracker.remove(ip);
            }
        }
    }
}
