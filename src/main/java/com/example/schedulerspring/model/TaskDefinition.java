package com.example.schedulerspring.model;

import lombok.Data;

enum ActionType {
    MONITOR,
    PROCESSOR,
    ACTUATOR
}

@Data
public class TaskDefinition {
    private String cronExpression;
    private ActionType actionType;
    private String data;
    private String appId;
    private Integer hoursToStore;
}