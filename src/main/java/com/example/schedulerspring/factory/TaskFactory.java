package com.example.schedulerspring.factory;

import com.example.schedulerspring.enums.ActionType;
import com.example.schedulerspring.model.MonitorTask;
import com.example.schedulerspring.model.TaskDefinition;

public class TaskFactory {

    public static Runnable builder(TaskDefinition taskDefinition) {
        Runnable runnable = null;
        if (taskDefinition.getActionType().equals(ActionType.MONITOR)) {
            runnable = new MonitorTask(taskDefinition);
        } else if (taskDefinition.getActionType().equals(ActionType.PROCESSOR)) {
            runnable = null; // implement processor here
        } else if (taskDefinition.getActionType().equals(ActionType.ACTUATOR)) {
            runnable = null; // implement actuator here
        }
        return runnable;
    }

}
