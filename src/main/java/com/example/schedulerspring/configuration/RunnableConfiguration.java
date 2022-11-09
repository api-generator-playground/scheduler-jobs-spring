package com.example.schedulerspring.configuration;

import com.example.schedulerspring.enums.ActionType;
import com.example.schedulerspring.model.MonitorTask;
import com.example.schedulerspring.model.ObserverTask;
import com.example.schedulerspring.model.TaskDefinition;
import com.example.schedulerspring.rules.MetricsActuator;
import com.example.schedulerspring.rules.MetricsDTO;
import com.example.schedulerspring.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@Configuration
@Slf4j
public class RunnableConfiguration {

    @Autowired
    TestService testService;

    @Autowired
    MetricsActuator metricsActuator;

    @Autowired
    MetricsTestConfiguration metricsTestConfiguration;

    @Bean(name = "monitorFactory")
    public Function<TaskDefinition, Runnable> monitorFactory() {
        return taskDefinition -> new MonitorTask(taskDefinition) {
            @Override
            public void run() {
                log.info("[MONITOR] Running action: " + taskDefinition.getActionType());
                testService.printTest();
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
        };
    }

    @Bean(name = "observerFactory")
    public Function<TaskDefinition, Runnable> observerFactory() {
        return td -> new ObserverTask(td) {
            @Override
            public void run() {
//                MetricsDTO metricsDTO = new MetricsDTO(0.1, 0.05, 25.0, 1000.0);
                log.info("[OBSERVER] Running action");
                metricsActuator.takeAction(metricsTestConfiguration.getMetricsDTO());
                log.info("[OBSERVER] With Data");
            }
        };
    }

}
