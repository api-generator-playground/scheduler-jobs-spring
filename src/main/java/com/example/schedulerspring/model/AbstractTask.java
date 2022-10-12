package com.example.schedulerspring.model;

public abstract class AbstractTask implements Runnable {

    protected TaskDefinition taskDefinition;

    public AbstractTask(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
