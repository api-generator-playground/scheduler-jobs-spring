package com.example.schedulerspring.controller;

import com.example.schedulerspring.configuration.MetricsTestConfiguration;
import com.example.schedulerspring.model.TaskDefinition;
import com.example.schedulerspring.model.MetricsDTO;
import com.example.schedulerspring.rules.GeneralRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.schedulerspring.rules.MetricsActuator;

@RestController
@RequestMapping(path = "/metricsTest")
public class MetricsTestController {

    @Autowired
    MetricsTestConfiguration metricsTestConfiguration;

    @Autowired
    MetricsActuator metricsActuator;

    @PutMapping(path="/changeMetrics")
    public ResponseEntity<?> changeMetrics(@RequestBody MetricsDTO metricsDTO) {
        metricsTestConfiguration.setMetricsDTO(metricsDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="/testAction")
    public ResponseEntity<String> testAction(@RequestBody MetricsDTO metricsDTO) {
        this.metricsActuator.takeAction(metricsDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="/testActionPlus")
    public ResponseEntity<String> testActionPlus(@RequestBody MetricsDTO metricsDTO) {
        this.metricsActuator.takeActionPlus(metricsDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="/setLimits")
    public ResponseEntity<String> setLimits(@RequestBody GeneralRules.MetricLimits limits) {
        this.metricsActuator.setLimits(limits);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
