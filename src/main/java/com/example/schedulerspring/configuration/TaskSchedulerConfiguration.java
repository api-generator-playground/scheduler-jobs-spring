package com.example.schedulerspring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TaskSchedulerConfiguration {

    @Bean(name = "taskSchedulerPool")
    public TaskScheduler taskSchedulerPool() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(100);
        return taskScheduler;
    }

    @Bean(name = "processorSchedulerPool")
    public TaskScheduler processorSchedulerPool() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        return taskScheduler;
    }

}
