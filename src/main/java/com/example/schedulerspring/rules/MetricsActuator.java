package com.example.schedulerspring.rules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetricsActuator {

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
            log.info("Your application is totally running at low consume");
        } else if (LowConsumeRule.isInPartialLowConsume(metricsDTO)) {
            log.info("Your application is partially running at low consume");
        } else if (HighConsumeRule.isInTotalHighConsume(metricsDTO)) {
            log.info("Your application is totally running at high consume");
        } else if (HighConsumeRule.isInPartialHighConsume(metricsDTO)) {
            log.info("Your application is partially running at high consume");
        }
    }
}
