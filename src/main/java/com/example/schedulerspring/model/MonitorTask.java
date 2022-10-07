package com.example.schedulerspring.model;

public class MonitorTask implements Runnable {
    private TaskDefinition taskDefinition;

    public MonitorTask(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    @Override
    public void run() {
        System.out.println("[MONITOR] Running action: " + taskDefinition.getActionType());
        System.out.println("[MONITOR] With Data: " + taskDefinition.getData());
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
