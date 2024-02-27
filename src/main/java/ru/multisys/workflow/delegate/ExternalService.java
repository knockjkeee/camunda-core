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
import org.springframework.web.client.RestTemplate;
import ru.multisys.workflow.database.dao.TasksDao;
import ru.multisys.workflow.database.entity.TasksEntity;
import ru.multisys.workflow.domain.NewTicket;
import ru.multisys.workflow.domain.StateTicket;
import ru.multisys.workflow.service.CheckedState;
import ru.multisys.workflow.service.OtrsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.Random;

/**
 * @author knockjkeee
 * @created 22/02/2024 - 16:07
 */
@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExternalService implements JavaDelegate {

    OtrsService otrsService;
    TasksDao tasksDao;

//    ObjectMapper objectMapper;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
//        Random random = new Random();
//        boolean res = random.nextBoolean();


//        String processDefinitionId = execution.getProcessDefinitionId();


        NewTicket ticket = (NewTicket) execution.getVariable("ticket");
        String id = ticket.getTicketID();
        String externalState = otrsService.getTask(id);
        ticket.setState(externalState);

        String target = (String) execution.getVariable("target");
        Instant now = Instant.now();

        TasksEntity tasks = tasksDao.findByProcessInstanceId(execution.getProcessInstanceId());
        boolean res = CheckedState.nextState(target, externalState);

        log.info(String.format("ExternalService target - %s, res - %s, externalState - %s", target, res, externalState));

        execution.setVariables(
                new HashMap<>(){{
                    if (StateTicket.END.nameLowerCase().equals(target) && res) {
                        put("closeTime", now.toString());

                        tasks.setState(StateTicket.CLOSE);
                        tasks.setCloseStamp(now);
                        tasksDao.save(tasks);
                    }
                    put("isChangeState", res);
                }}
        );

    }
}
