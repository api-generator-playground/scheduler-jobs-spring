package com.example.schedulerspring.service;

import com.example.schedulerspring.model.AbstractTask;
import com.example.schedulerspring.model.TaskDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class TaskSchedulingService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private TaskDefinitionService taskDefinitionService;

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();
    Map<String, TaskDefinition> jobsDefinition = new HashMap<>();

    public String scheduleATask(String jobId, Runnable tasklet, String cronExpression) {
        log.info("Scheduling task with job id: " + jobId + " and cron expression: " + cronExpression);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(tasklet, new CronTrigger(cronExpression,
                TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(jobId, scheduledTask);
        jobsDefinition.put(jobId, ((AbstractTask) tasklet).getTaskDefinition());
        taskDefinitionService.createTaskDefinition(((AbstractTask) tasklet).getTaskDefinition());
        return jobId;
    }

    public void removeScheduledTask(String jobId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.remove(jobId);
            jobsDefinition.remove(jobId);
        }
    }

    public List<String> listAllJobsById() {
        return new ArrayList<>(jobsMap.keySet());
    }

    public TaskDefinition getJobById(String id) {
        return jobsDefinition.get(id);
    }
}
