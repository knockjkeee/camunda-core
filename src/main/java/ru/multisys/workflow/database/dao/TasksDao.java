package ru.multisys.workflow.database.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.multisys.workflow.database.entity.ExternalHistoryEntity;
import ru.multisys.workflow.database.entity.TasksEntity;
import ru.multisys.workflow.database.repo.TaskRepo;
import ru.multisys.workflow.domain.StateTicket;

import java.util.List;
import java.util.Optional;

/**
 * @author knockjkeee
 * @created 27/02/2024 - 12:23
 */
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TasksDao {

    TaskRepo taskRepo;

    @Transactional
    public void save(TasksEntity entity) {
        taskRepo.save(entity);
    }

    @Transactional
    public TasksEntity findByProcessInstanceId(String processId) {
        Optional<TasksEntity> byName = taskRepo.findByProcessInstanceId(processId);
        return byName.orElse(null);
    }

    @Transactional
    public void updateStateAndHistory(TasksEntity entity, StateTicket newState, ExternalHistoryEntity history) {
        TasksEntity tasks = findByProcessInstanceId(entity.getProcessInstanceId());

        tasks.setState(newState);
        List<ExternalHistoryEntity> externalHistory = tasks.getExternalHistory();

        history.setTasksEntity(tasks);
        externalHistory.add(history);

        save(tasks);
    }


    @Transactional
    public void updateHistory(TasksEntity entity, ExternalHistoryEntity history) {
        TasksEntity byProcessInstanceId = findByProcessInstanceId(entity.getProcessInstanceId());

        if (byProcessInstanceId != null) {
            history.setTasksEntity(byProcessInstanceId);

            save(byProcessInstanceId);
        } else {

            history.setTasksEntity(entity);
            save(entity);
        }

    }

    @Transactional
    public List<TasksEntity> getAll() {
        return taskRepo.findAll();
    }
}
