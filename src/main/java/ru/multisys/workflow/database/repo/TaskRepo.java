package ru.multisys.workflow.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.multisys.workflow.database.entity.TasksEntity;

import java.util.Optional;

/**
 * @author knockjkeee
 * @created 27/02/2024 - 12:23
 */
public interface TaskRepo extends JpaRepository<TasksEntity, Long> {
    Optional<TasksEntity> findByProcessInstanceId(String processId);
}
