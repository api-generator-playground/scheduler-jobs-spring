package com.example.schedulerspring.service;

import com.example.schedulerspring.enums.ActionType;
import com.example.schedulerspring.model.AbstractTask;
import com.example.schedulerspring.model.TaskDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskSchedulingService {

    @Autowired
    @Qualifier("taskSchedulerPool")
    private TaskScheduler taskScheduler;

    @Autowired
    @Qualifier("processorSchedulerPool")
    private TaskScheduler processorSchedulerPool;

    @Autowired
    private TaskDefinitionService taskDefinitionService;
    Map<String, Pair<ScheduledFuture<?>, TaskDefinition>> jobsMap = new HashMap<>();

    private void logScheduler(TaskDefinition taskDefinition) {
        log.info(String.format("Scheduling task with job id: %s and cron expression: %s",
                taskDefinition.getId(), taskDefinition.getCronExpression()));
    }

    public String scheduleATask(Runnable tasklet) {
        TaskDefinition taskDefinition = taskDefinitionService.createTaskDefinition(((AbstractTask) tasklet)
                .getTaskDefinition());

        putTaskIntoScheduler(tasklet);
        return taskDefinition.getId().toString();
    }

    public void putTaskIntoScheduler(Runnable task) {
        TaskDefinition taskDefinition = ((AbstractTask) task).getTaskDefinition();
        logScheduler(taskDefinition);
        
        ScheduledFuture<?> scheduledTask = null;
        if (taskDefinition.getActionType() == ActionType.PROCESSOR) {
            scheduledTask = processorSchedulerPool.schedule(task, new CronTrigger(
                    taskDefinition.getCronExpression(), TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        } else {
            scheduledTask = taskScheduler.schedule(task, new CronTrigger(
                    taskDefinition.getCronExpression(), TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        }
        
        jobsMap.put(taskDefinition.getId().toString(), new MutablePair<>(scheduledTask,
                ((AbstractTask) task).getTaskDefinition()));
    }

    public void removeScheduledTask(String jobId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId).getLeft();
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            taskDefinitionService.removeTaskDefinition(jobsMap.get(jobId).getRight());
            jobsMap.remove(jobId);
        }
    }

    public List<TaskDefinition> listAllJobs() {
        return jobsMap.values().stream().map(Pair::getRight).collect(Collectors.toList());
    }

    public TaskDefinition getJobById(String id) {
        return jobsMap.get(id).getRight();
    }
}
