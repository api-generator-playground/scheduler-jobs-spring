package com.example.schedulerspring.model;

import com.example.schedulerspring.enums.ActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "TASKDEFINITION")
@Data
public class TaskDefinition {

    @Id
    @GeneratedValue
    @Column( columnDefinition = "uuid", updatable = false )
    private UUID id;

    @Schema(example = "0 * * * * *")
    private String cronExpression;

    @Schema(example = "MONITOR")
    private ActionType actionType;

    @Schema(example = "Metric data is been stored")
    private String data;

    @Schema(example = "8b1e33c2-a4de-42f1-abbc-af9136ebec15")
    private String appId;

    private Integer hoursToStore;
}