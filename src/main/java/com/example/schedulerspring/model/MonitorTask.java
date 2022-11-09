package com.example.schedulerspring.model;

import com.example.schedulerspring.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;

@Slf4j
public class MonitorTask extends AbstractTask {

    public MonitorTask(TaskDefinition taskDefinition) {
        super(taskDefinition);
    }

    @Override
    public void run() {
        log.info("[MONITOR] Running action: " + taskDefinition.getActionType());
        try {
            Thread.sleep(1000);
            CronExpression cronExpression = CronExpression.parse(taskDefinition.getCronExpression());
            LocalDateTime nextRunDate = cronExpression.next(LocalDateTime.now());
            log.info(nextRunDate.toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("[MONITOR] With Data: " + taskDefinition.getData());
    }
}
