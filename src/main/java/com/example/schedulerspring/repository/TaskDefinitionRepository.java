package com.example.schedulerspring.repository;

import com.example.schedulerspring.model.TaskDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDefinitionRepository extends JpaRepository<TaskDefinition, Long> {
}
