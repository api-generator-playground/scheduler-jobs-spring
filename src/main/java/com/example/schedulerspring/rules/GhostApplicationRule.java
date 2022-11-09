package com.example.schedulerspring.rules;

public class GhostApplicationRule {

    public static boolean isGhostApplication(MetricsDTO metricsDTO) {
        boolean isGhostApplication = false;
        if (metricsDTO.getCpu() == 0.0 &&
            metricsDTO.getMemory() == 0.0 &&
            metricsDTO.getReqs() == 0.0 &&
            metricsDTO.getReqs_latency() == 0.0
        ) {
            isGhostApplication = true;
        }
        return isGhostApplication;
    }
}
