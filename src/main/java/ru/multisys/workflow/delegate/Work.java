package ru.multisys.workflow.delegate;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import ru.multisys.workflow.domain.StateTicket;

import java.time.Instant;
import java.util.HashMap;

/**
 * @author knockjkeee
 * @created 22/02/2024 - 18:35
 */
@Log4j2
public class Work implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        log.info("Work!!!");

        execution.setVariables(
                new HashMap<>(){{
                    put("workTime", Instant.now().toString());
                    put("target", StateTicket.WORK.nameLowerCase());
                }}
        );

    }
}
