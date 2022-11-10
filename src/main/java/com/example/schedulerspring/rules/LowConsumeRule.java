package com.example.schedulerspring.rules;

import com.example.schedulerspring.model.MetricsDTO;

public class LowConsumeRule {
    private static final Double CPU_THRESHOLD = 0.2;
    private static final Double MEMORY_THRESHOLD = 0.1;
    private static final Double REQS_THRESHOLD = 100.0;
    private static final Double REQS_LATENCY_THRESHOLD = 2000.0;

    public static boolean isInTotalLowConsume(MetricsDTO metricsDTO) {
        boolean isInLowConsume = false;
        if (metricsDTO.getCpu() < CPU_THRESHOLD &&
            metricsDTO.getMemory() < MEMORY_THRESHOLD &&
            metricsDTO.getReqs() < REQS_THRESHOLD &&
            metricsDTO.getReqs_latency() < REQS_LATENCY_THRESHOLD) {
            isInLowConsume = true;
        }
        return isInLowConsume;
    }

    public static boolean isInPartialLowConsume(MetricsDTO metricsDTO) {
        boolean isInLowConsume = false;
        if (metricsDTO.getCpu() < CPU_THRESHOLD ||
                metricsDTO.getMemory() < MEMORY_THRESHOLD ||
                metricsDTO.getReqs() < REQS_THRESHOLD ||
                metricsDTO.getReqs_latency() < REQS_LATENCY_THRESHOLD) {
            isInLowConsume = true;
        }
        return isInLowConsume;
    }
}
