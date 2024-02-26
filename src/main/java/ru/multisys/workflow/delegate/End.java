package ru.multisys.workflow.delegate;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import ru.multisys.workflow.domain.StateTicket;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author knockjkeee
 * @created 20/02/2024 - 12:45
 */
@Log4j2
public class End implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        log.info("End!!!");

        execution.setVariables(
                new HashMap<>(){{
                    put("timeEnd", Instant.now().toString());
                    put("target", StateTicket.END.nameLowerCase());
                }}
        );
    }
}
