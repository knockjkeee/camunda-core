package ru.multisys.workflow.delegate;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @author knockjkeee
 * @created 22/02/2024 - 18:09
 */
public class Msg implements JavaDelegate {

    private final ProcessEngine processEngine;

    public Msg(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService
                .createMessageCorrelation("EventStart")
                .setVariable("rere", "rere").correlate();

    }
}
