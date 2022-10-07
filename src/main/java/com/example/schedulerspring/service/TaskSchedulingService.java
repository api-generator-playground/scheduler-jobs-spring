package com.example.schedulerspring.service;

import com.example.schedulerspring.model.MonitorTask;
import com.example.schedulerspring.model.TaskDefinition;
import com.example.schedulerspring.model.TaskDefinitionRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
public class TaskSchedulingService {

    @Autowired
    private TaskScheduler taskScheduler;

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();
    Map<String, TaskDefinition> jobsDefinition = new HashMap<>();

    public String scheduleATask(String jobId, Runnable tasklet, String cronExpression) {
        System.out.println("Scheduling task with job id: " + jobId + " and cron expression: " + cronExpression);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(tasklet, new CronTrigger(cronExpression,
                TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(jobId, scheduledTask);
        jobsDefinition.put(jobId, ((MonitorTask) tasklet).getTaskDefinition());
        return jobId;
    }

    public void removeScheduledTask(String jobId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(jobId, null);
        }
    }

    public List<String> listAllJobsById() {
        return new ArrayList<>(jobsMap.keySet());
    }

    public TaskDefinition getJobById(String id) {
        return jobsDefinition.get(id);
    }
}
