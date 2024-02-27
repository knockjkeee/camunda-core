package ru.multisys.workflow.delegate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.multisys.workflow.database.dao.TasksDao;
import ru.multisys.workflow.database.entity.TasksEntity;
import ru.multisys.workflow.domain.StateTicket;

import java.time.Instant;
import java.util.HashMap;

/**
 * @author knockjkeee
 * @created 22/02/2024 - 18:35
 */
@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Work implements JavaDelegate {

    private static final String NAME_TIME = "workTime";

    TasksDao tasksDao;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        log.info("Work!!!");

        execution.setVariables(
                new HashMap<>(){{
                    if (Strings.isEmpty(((String) execution.getVariable(NAME_TIME)))){
                        Instant now = Instant.now();
                        put(NAME_TIME, now.toString());

                        TasksEntity tasks = tasksDao.findByProcessInstanceId(execution.getProcessInstanceId());
                        tasks.setState(StateTicket.WORK);
                        tasks.setWorkStamp(now);
                        tasksDao.save(tasks);
                    }
                    put("target", StateTicket.WORK.nameLowerCase());
                }}
        );

    }
}
