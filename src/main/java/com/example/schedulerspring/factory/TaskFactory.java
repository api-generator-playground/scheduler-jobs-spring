package com.example.schedulerspring.factory;

import com.example.schedulerspring.enums.ActionType;
import com.example.schedulerspring.model.MonitorTask;
import com.example.schedulerspring.model.ProcessorTask;
import com.example.schedulerspring.model.TaskDefinition;
import com.example.schedulerspring.rules.MetricsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TaskFactory {

    @Autowired
    @Qualifier("monitorFactory")
    private Function<TaskDefinition, Runnable> monitorFactory;

    @Autowired
    @Qualifier("observerFactory")
    private Function<TaskDefinition, Runnable> observerFactory;

    public Runnable builder(TaskDefinition taskDefinition) {
        Runnable runnable = null;
        if (taskDefinition.getActionType().equals(ActionType.MONITOR)) {
            runnable = monitorFactory.apply(taskDefinition);
        } else if (taskDefinition.getActionType().equals(ActionType.PROCESSOR)) {
            runnable = new ProcessorTask(taskDefinition);
        } else if (taskDefinition.getActionType().equals(ActionType.ACTUATOR)) {
            runnable = null; // implement actuator here
        } else if (taskDefinition.getActionType().equals(ActionType.OBSERVER)) {
            runnable = observerFactory.apply(taskDefinition);
        }
        return runnable;
    }

}
