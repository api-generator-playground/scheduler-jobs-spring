package com.example.schedulerspring.rules;

import com.example.schedulerspring.model.MetricsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetricsActuator {

    @Autowired
    private GeneralRules generalRules;

    /*
        Ideia de Vitor: Realizar uma combinação com todas as métricas e cada combinação dessa
        ter uma ação específica. Por exemplo:
            cpu (min: 0.1, max: 0.7)
            memory (min: 0.1, max: 0.8)
            requests (min: 100, max: 3000)
            requests_latency (min: 100, max: 3000)
            Fazendo a combinação desses valores teríamos 2*2*2*2(16) combinações.
            Usando a quadrupla (cpu,memory,requests,requests_latency):
                Para combinação (0.1, 0.1, 100, 100) temos a acão [DESÇA UMA INSTÂNCIA SE HOUVER MAIS DE UMA]

        O método abaixo simplifica as combinações, agrupando em Ghost, Baixa e Alta. Sendo que baixa e alta possuem
        variações parciais e totais.
     */
    public void takeAction(MetricsDTO metricsDTO) {
        if (GhostApplicationRule.isGhostApplication(metricsDTO)) {
            log.info("Your application is operating at ghost mode. Stop this application to save resources.");
        }
        if (LowConsumeRule.isInTotalLowConsume(metricsDTO)) {
            log.info("Your application is totally running at low consume. " +
                        "Try to scale down one instance to save resources.");
        } else if (LowConsumeRule.isInPartialLowConsume(metricsDTO)) {
            log.info("Your application is partially running at low consume. " +
                        "Try to scale down one instance to save resources.");
        } else if (HighConsumeRule.isInTotalHighConsume(metricsDTO)) {
            log.info("Your application is totally running at high consume. " +
                        "Try to scale up one instance to improve the performance.");
        } else if (HighConsumeRule.isInPartialHighConsume(metricsDTO)) {
            log.info("Your application is partially running at high consume. " +
                    "Try to scale up one instance to improve the performance.");
        }
    }


    public void takeActionPlus(MetricsDTO metricsDTO) {
        GeneralRules.MetricClassification mc = this.generalRules.getClassification(metricsDTO);
        if (mc == null) { log.info("The limits for this application wasn't set!"); }
        else {
            log.info(String.format("Your application has its CPU in a %s USE." + System.lineSeparator() +
                    "The Memory is at a %s USE." + System.lineSeparator() +
                    "The Number of Requests is considered %s." + System.lineSeparator() +
                    "The latency is considered %s.", mc.getCpu(), mc.getMemory(), mc.getRequests(), mc.getLatency()));
        }
    }

    public void setLimits(GeneralRules.MetricLimits limits) {
        this.generalRules.setLimits(limits);
    }
}
