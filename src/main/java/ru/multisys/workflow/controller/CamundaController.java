package ru.multisys.workflow.controller;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.multisys.workflow.domain.base.NewTicket;

import java.util.HashMap;
import java.util.Map;

/**
 * @author knockjkeee
 * @created 21/02/2024 - 13:31
 */
@Log4j2
@RestController
public class CamundaController {

    private final ProcessEngine processEngine;

    public CamundaController(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }


    @PostMapping("/startEvent")
    public ResponseEntity<NewTicket> newTicket(@RequestBody NewTicket data) {

        if (data == null) {
            log.error("При создании новой заявки возникли проблемы при парсинге входящего обьекта");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        log.info("startEvent -> Создание новой заявки №{}", data.ticketNumber());

        Map<String, Object> req = new HashMap<>();
        req.put("ticket", data);
        processEngine.getRuntimeService().startProcessInstanceByMessage("StartProcess", "pub-1", req);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
