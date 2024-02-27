package ru.multisys.workflow.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class Init implements JavaDelegate {

    private static final String NAME_TIME = "initTime";

    TasksDao tasksDao;
    ObjectMapper objectMapper;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Init!!!");
        String now = ZonedDateTime.now().toLocalDateTime().toString();

        NewTicket ticket = (NewTicket)execution.getVariable("ticket");

        TasksEntity newTasks = new TasksEntity();
        newTasks.setInitStamp(now);
        newTasks.setState(StateTicket.INIT);
        newTasks.setProcessInstanceId(execution.getProcessInstanceId());
        newTasks.setTicketID(ticket.getTicketID());
        newTasks.setTicketNumber(ticket.getTicketNumber());
        newTasks.setCustomerUserID(ticket.getCustomerUserID());
        newTasks.setUserID(ticket.getUserID());
        tasksDao.save(newTasks);

        execution.setVariables(new HashMap<>() {{
            put(NAME_TIME, now);
            put("target", StateTicket.INIT.nameLowerCase());
        }});
    }
}
