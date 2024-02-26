package ru.multisys.workflow.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.multisys.workflow.domain.NewTicket;
import ru.multisys.workflow.domain.StateTicket;

import java.time.Instant;
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

    ObjectMapper objectMapper;

    @Override
    public void execute(DelegateExecution execution) throws Exception {


        NewTicket ticket = (NewTicket) execution.getVariable("ticket");

        String now = Instant.now().toString();

        log.info("Start!!!");

        execution.setVariables(
                new HashMap<>(){{
                    put("workTime", now);
                    put("target", StateTicket.START.nameLowerCase());
                    put("ticketEntity", objectMapper.writeValueAsString(ticket));
                }}
        );
    }
}
