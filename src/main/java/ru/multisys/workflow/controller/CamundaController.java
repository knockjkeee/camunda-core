package ru.multisys.workflow.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.multisys.workflow.domain.NewTicket;
import ru.multisys.workflow.service.OtrsService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author knockjkeee
 * @created 21/02/2024 - 13:31
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CamundaController {

    ProcessEngine processEngine;

    OtrsService otrsService;

    @PostMapping("/startEvent")
    public ResponseEntity<NewTicket> newTicket(@RequestBody NewTicket data) {

        if (data == null) {
            log.error("При создании новой заявки возникли проблемы при парсинге входящего обьекта");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        log.info("startEvent -> Создание новой заявки №{}", data.getTicketNumber());

        Map<String, Object> req = new HashMap<>();
        req.put("ticket", data);

//        IntStream.range(0, 10).forEach(e -> {
//            processEngine.getRuntimeService().startProcessInstanceByMessage("StartProcess", "pub-1", req);
//        });

        processEngine.getRuntimeService().startProcessInstanceByMessage("StartProcess", "pub-1", req);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<String> test(@PathVariable("id") String id) {
//        String state = otrsService.getTask(90858L);
        String state = otrsService.getTask(id);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }



}
