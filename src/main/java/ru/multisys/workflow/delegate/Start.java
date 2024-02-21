package ru.multisys.workflow.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import ru.multisys.workflow.domain.base.NewTicket;

import java.time.Instant;

/**
 * @author knockjkeee
 * @created 20/02/2024 - 12:45
 */
public class Start implements JavaDelegate {

//    private final ObjectMapper objectMapper;

//    public Start(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String now = Instant.now().toString();
        NewTicket ticket = (NewTicket) execution.getVariable("ticket");

        execution.setVariable("timeStart", now);
        execution.setVariable("ticketEntity", objectMapper.writeValueAsString(ticket));
    }
}
