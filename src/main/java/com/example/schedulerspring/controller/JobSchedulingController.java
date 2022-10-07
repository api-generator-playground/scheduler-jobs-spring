package com.example.schedulerspring.controller;

import com.example.schedulerspring.model.TaskDefinition;
import com.example.schedulerspring.model.TaskDefinitionRunnable;
import com.example.schedulerspring.service.TaskSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/schedule")
public class JobSchedulingController {

    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @PostMapping(path="/taskdef", consumes = "application/json", produces="application/json")
    public void scheduleATask(@RequestBody TaskDefinition taskDefinition) {
        taskSchedulingService.scheduleATask(UUID.randomUUID().toString(),
                                            new TaskDefinitionRunnable(taskDefinition),
                                            taskDefinition.getCronExpression());
    }

    @GetMapping(path="/remove/{jobid}")
    public void removeJob(@PathVariable String jobid) {
        taskSchedulingService.removeScheduledTask(jobid);
    }

    @GetMapping(path="/allJobs")
    public ResponseEntity<?> getAllJobsIds() {
        return new ResponseEntity<>(taskSchedulingService.listAllJobsById(), HttpStatus.OK);
    }
}