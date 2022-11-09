package com.example.schedulerspring.controller;

import com.example.schedulerspring.factory.TaskFactory;
import com.example.schedulerspring.model.TaskDefinition;
import com.example.schedulerspring.service.TaskSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping(path = "/schedule")
public class JobSchedulingController {

    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @Autowired
    TaskFactory taskFactory;

    @PostMapping(path="/taskdef")
    public ResponseEntity<?> scheduleATask(@RequestBody TaskDefinition taskDefinition) {
        String jobId = taskSchedulingService.scheduleATask(taskFactory.builder(taskDefinition));
        return new ResponseEntity<>(jobId, HttpStatus.OK);
    }

    @DeleteMapping(path="/remove/{jobId}")
    public ResponseEntity<?> removeJob(@PathVariable String jobId) {
        taskSchedulingService.removeScheduledTask(jobId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/get/{jobId}")
    public ResponseEntity<?> getJobInfo(@PathVariable String jobId) {
        TaskDefinition taskDefinition = taskSchedulingService.getJobById(jobId);
        return new ResponseEntity<>(taskDefinition, HttpStatus.OK);
    }

    @GetMapping(path="/allJobs")
    public ResponseEntity<?> getAllJobsIds() {
        return new ResponseEntity<>(taskSchedulingService.listAllJobs(), HttpStatus.OK);
    }
}