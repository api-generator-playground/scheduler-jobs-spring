package com.example.schedulerspring.rules;

import com.example.schedulerspring.model.MetricsDTO;

public class HighConsumeRule {
    private static final Double CPU_THRESHOLD = 0.7;
    private static final Double MEMORY_THRESHOLD = 0.8;
    private static final Double REQS_THRESHOLD = 2000.0;
    private static final Double REQS_LATENCY_THRESHOLD = 3000.0;

    public static boolean isInTotalHighConsume(MetricsDTO metricsDTO) {
        boolean isInHighConsume = false;
        if (metricsDTO.getCpu() > CPU_THRESHOLD &&
                metricsDTO.getMemory() > MEMORY_THRESHOLD &&
                metricsDTO.getReqs() > REQS_THRESHOLD &&
                metricsDTO.getReqs_latency() > REQS_LATENCY_THRESHOLD) {
            isInHighConsume = true;
        }
        return isInHighConsume;
    }

    public static boolean isInPartialHighConsume(MetricsDTO metricsDTO) {
        boolean isInHighConsume = false;
        if (metricsDTO.getCpu() > CPU_THRESHOLD ||
                metricsDTO.getMemory() > MEMORY_THRESHOLD ||
                metricsDTO.getReqs() > REQS_THRESHOLD ||
                metricsDTO.getReqs_latency() > REQS_LATENCY_THRESHOLD) {
            isInHighConsume = true;
        }
        return isInHighConsume;
    }
}
