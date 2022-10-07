package com.example.schedulerspring.controller;

import com.example.schedulerspring.factory.TaskFactory;
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
    public ResponseEntity<?> scheduleATask(@RequestBody TaskDefinition taskDefinition) {
        String jobId = taskSchedulingService.scheduleATask(UUID.randomUUID().toString(),
                                            TaskFactory.builder(taskDefinition),
                                            taskDefinition.getCronExpression());
        return new ResponseEntity<>(jobId, HttpStatus.OK);
    }

    @GetMapping(path="/remove/{jobid}")
    public void removeJob(@PathVariable String jobid) {
        taskSchedulingService.removeScheduledTask(jobid);
    }

    @GetMapping(path="/get/{jobid}")
    public ResponseEntity<?> getJobInfo(@PathVariable String jobid) {
        TaskDefinition taskDefinition = taskSchedulingService.getJobById(jobid);
        return new ResponseEntity<>(taskDefinition, HttpStatus.OK);
    }

    @GetMapping(path="/allJobs")
    public ResponseEntity<?> getAllJobsIds() {
        return new ResponseEntity<>(taskSchedulingService.listAllJobsById(), HttpStatus.OK);
    }
}