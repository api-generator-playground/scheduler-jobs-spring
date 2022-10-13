package com.example.schedulerspring.repository;

import com.example.schedulerspring.model.TaskDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface TaskDefinitionRepository extends JpaRepository<TaskDefinition, Long> {

    @Transactional
    void deleteById(UUID id);
}
