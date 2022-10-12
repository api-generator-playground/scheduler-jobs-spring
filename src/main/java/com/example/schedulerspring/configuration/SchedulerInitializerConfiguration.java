package com.example.schedulerspring.configuration;

import com.example.schedulerspring.factory.TaskFactory;
import com.example.schedulerspring.repository.TaskDefinitionRepository;
import com.example.schedulerspring.service.TaskDefinitionService;
import com.example.schedulerspring.service.TaskSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SchedulerInitializerConfiguration {

    @Autowired
    TaskDefinitionRepository taskDefinitionRepository;

    @Autowired
    TaskSchedulingService taskSchedulingService;

    @PostConstruct
    public void postConstruct() {
        taskDefinitionRepository.findAll().forEach(tf -> {
            taskSchedulingService.scheduleATask(
                    tf.getId().toString(),
                    TaskFactory.builder(tf),
                    tf.getCronExpression());
        });
    }
}
