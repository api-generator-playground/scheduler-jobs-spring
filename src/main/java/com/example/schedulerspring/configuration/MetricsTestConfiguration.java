package com.example.schedulerspring.configuration;

import com.example.schedulerspring.model.MetricsDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsTestConfiguration {
    private MetricsDTO metricsDTO = new MetricsDTO(0.1, 0.1, 10.0, 100.0);

    public MetricsDTO getMetricsDTO() {
        return this.metricsDTO;
    }

    public void setMetricsDTO(MetricsDTO metricsDTO) {
        this.metricsDTO = metricsDTO;
    }
}
