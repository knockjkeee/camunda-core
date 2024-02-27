package ru.multisys.workflow.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.commons.utils.StringUtil;
import org.springframework.stereotype.Component;
import ru.multisys.workflow.database.dao.TasksDao;
import ru.multisys.workflow.database.entity.TasksEntity;
import ru.multisys.workflow.domain.NewTicket;
import ru.multisys.workflow.domain.StateTicket;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * @author knockjkeee
 * @created 20/02/2024 - 12:45
 */

@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Start implements JavaDelegate {

//    private final ObjectMapper objectMapper;

//    public Start(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }

    private static final String NAME_TIME = "newTime";

    ObjectMapper objectMapper;

    TasksDao tasksDao;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Start!!!");

        execution.setVariables(new HashMap<>() {{
            if (Strings.isEmpty(((String) execution.getVariable(NAME_TIME)))) {
                String now = ZonedDateTime.now().toLocalDateTime().toString();
                put(NAME_TIME, now.toString());
                put("target", StateTicket.START.nameLowerCase());

                TasksEntity tasks = tasksDao.findByProcessInstanceId(execution.getProcessInstanceId());

                if (tasks == null) {

                    TasksEntity newTasks = new TasksEntity();
//        tasks.setProcessInstanceId(processInstanceId);
                    newTasks.setInitStamp((String) execution.getVariable("initTime"));
                    newTasks.setStartStamp(now);
                    newTasks.setState(StateTicket.START);
                    newTasks.setProcessInstanceId(execution.getProcessInstanceId());
                    tasksDao.save(newTasks);
                } else {
                    tasks.setState(StateTicket.START);
                    tasks.setStartStamp(now);
                    tasksDao.save(tasks);
                }
            }
//                    put("target", StateTicket.START.nameLowerCase());
//                    put("ticketEntity", objectMapper.writeValueAsString(ticket));
        }});
    }
}
