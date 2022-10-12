package com.example.schedulerspring.service;

import com.example.schedulerspring.model.TaskDefinition;
import com.example.schedulerspring.repository.TaskDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDefinitionService {

    @Autowired
    private TaskDefinitionRepository taskDefinitionRepository;

    public TaskDefinition createTaskDefinition(TaskDefinition taskDefinition) {
        return taskDefinitionRepository.save(taskDefinition);
    }
}
