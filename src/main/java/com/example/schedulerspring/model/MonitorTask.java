package com.example.schedulerspring.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorTask extends AbstractTask {

    public MonitorTask(TaskDefinition taskDefinition) {
        super(taskDefinition);
    }

    @Override
    public void run() {
        log.info("[MONITOR] Running action: " + taskDefinition.getActionType());
        log.info("[MONITOR] With Data: " + taskDefinition.getData());
    }
}
