package com.example.schedulerspring.model;

import com.example.schedulerspring.enums.ActionType;
import lombok.Data;
@Data
public class TaskDefinition {
    private String cronExpression;
    private ActionType actionType;
    private String data;
    private String appId;
    private Integer hoursToStore;
}