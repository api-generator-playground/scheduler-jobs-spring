package com.example.schedulerspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricsDTO {
    private Double cpu;
    private Double memory;
    private Double reqs;
    private Double reqs_latency;
}
