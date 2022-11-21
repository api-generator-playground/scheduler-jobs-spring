package com.example.schedulerspring.rules;

import com.example.schedulerspring.model.MetricsDTO;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class GeneralRules {

    public enum Classifications {
        HIGH, MEDIUM, LOW;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MetricLimits {
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class MetricLimit {
            private Double downLimit;
            private Double upLimit;
        }

        private MetricLimit cpuLimits;
        private MetricLimit memoryLimits;
        private MetricLimit requestsLimits;
        private MetricLimit latencyLimits;
    }

    private MetricLimits limits;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class MetricClassification {

        private Classifications cpu;
        private Classifications memory;
        private Classifications requests;
        private Classifications latency;

    }

    public MetricClassification getClassification(MetricsDTO metricsDTO) {

        if (limits == null) { return null; }

        MetricClassification mc = new MetricClassification();

        mc.setCpu(this.getClassificationCpu(metricsDTO.getCpu()));
        mc.setMemory(this.getClassificationMemory(metricsDTO.getMemory()));
        mc.setRequests(this.getClassificationRequests(metricsDTO.getReqs()));
        mc.setLatency(this.getClassificationLatency(metricsDTO.getReqs_latency()));

        return mc;
    }

    private Classifications getClassificationCpu(Double value) {
        if (value <= this.limits.getCpuLimits().getDownLimit()) return Classifications.LOW;
        if (value <= this.limits.getCpuLimits().getUpLimit()) return Classifications.MEDIUM;
        return Classifications.HIGH;
    }

    private Classifications getClassificationMemory(Double value) {
        if (value <= this.limits.getMemoryLimits().getDownLimit()) return Classifications.LOW;
        if (value <= this.limits.getMemoryLimits().getUpLimit()) return Classifications.MEDIUM;
        return Classifications.HIGH;
    }

    private Classifications getClassificationRequests(Double value) {
        if (value <= this.limits.getRequestsLimits().getDownLimit()) return Classifications.LOW;
        if (value <= this.limits.getRequestsLimits().getUpLimit()) return Classifications.MEDIUM;
        return Classifications.HIGH;
    }

    private Classifications getClassificationLatency(Double value) {
        if (value <= this.limits.getLatencyLimits().getDownLimit()) return Classifications.LOW;
        if (value <= this.limits.getLatencyLimits().getUpLimit()) return Classifications.MEDIUM;
        return Classifications.HIGH;
    }


}
