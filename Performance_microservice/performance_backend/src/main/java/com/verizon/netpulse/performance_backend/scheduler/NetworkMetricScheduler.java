package com.verizon.netpulse.performance_backend.scheduler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.verizon.netpulse.performance_backend.service.NetworkMetricsService;

@Component
public class NetworkMetricScheduler {

    @Autowired
    private NetworkMetricsService metricService;

    @Scheduled(fixedRate = 30000)
    public void scheduleMetricCollection() throws IOException {
        metricService.measureAndSavingMetrics();
    }
}

