package ru.multisys.workflow.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.time.Instant;

/**
 * @author knockjkeee
 * @created 20/02/2024 - 12:45
 */
public class End implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("timeEnd", Instant
                .now().toString());
    }
}
