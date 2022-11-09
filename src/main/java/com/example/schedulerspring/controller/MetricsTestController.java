package com.example.schedulerspring.controller;

import com.example.schedulerspring.configuration.MetricsTestConfiguration;
import com.example.schedulerspring.model.TaskDefinition;
import com.example.schedulerspring.rules.MetricsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/metricsTest")
public class MetricsTestController {

    @Autowired
    MetricsTestConfiguration metricsTestConfiguration;

    @PutMapping(path="/changeMetrics")
    public ResponseEntity<?> changeMetrics(@RequestBody MetricsDTO metricsDTO) {
        metricsTestConfiguration.setMetricsDTO(metricsDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
