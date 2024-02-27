package ru.multisys.workflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.multisys.workflow.domain.NewTicket;
import ru.multisys.workflow.domain.handleServices.HandleServices;

import java.util.stream.IntStream;

@Log4j2
@RestController
public class KafkaController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

    private final KafkaTemplate<String, Object> template;
    private final String topicName;
    private final int messagesPerRequest;

    private final ObjectMapper objectMapper;
//    private CountDownLatch latch;

    public KafkaController(final KafkaTemplate<String, Object> template, @Value("${tpd.topic-name}") final String topicName, @Value("${tpd.messages-per-request}") final int messagesPerRequest, ObjectMapper objectMapper) {
        this.template = template;
        this.topicName = topicName;
        this.messagesPerRequest = messagesPerRequest;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/test")
    public String test() throws Exception {
//        latch = new CountDownLatch(messagesPerRequest);
        IntStream
                .range(0, messagesPerRequest)
                .forEach(i -> this.template.send(topicName,
                        String.valueOf(i),
                        new NewTicket("90854",
                                "2024022146000077",
                                "inspark",
                                "inspark",
                                "inspark_otrs@multi-sys.ru",
                                "2024-02-21 13:48:08",
                                "open")));
//        latch.await(60, TimeUnit.SECONDS);
        logger.info("All messages received");
        return "Hello Kafka!";
    }

    @PostMapping("/newTicket")
    public ResponseEntity<HandleServices> newTicket(@RequestBody String json) {
        HandleServices handle = null;
        try {
            handle = objectMapper.readValue(json, HandleServices.class);
        } catch (JsonProcessingException e) {
            log.error("При создании новой заявки возникли проблемы при парсинге входящего обьекта");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        NewTicket practicalAdvice = new NewTicket(handle.getEvent().getTicketID(),
                handle.getTicket().getTicketNumber(),
                handle.getTicket().getCustomerUserID(),
                handle.getTicket().getCustomerUser().getUserID(),
                handle.getTicket().getCustomerUser().getUserEmail(),
                handle.getTicket().getCreated(),
                handle.getTicket().getState()
        );
        this.template.send(topicName, "newTicket", practicalAdvice);
        log.info("Создание новой заявки №{}", handle.getTicket().getTicketNumber());
        return new ResponseEntity<>(handle, HttpStatus.OK);
    }

    @PostMapping("/ticket")
    public ResponseEntity<NewTicket> newTicket(@RequestBody NewTicket data) {

        if (data == null) {
            log.error("При создании новой заявки возникли проблемы при парсинге входящего обьекта");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        this.template.send(topicName, "ticket", data);
        log.info("Создание новой заявки №{}", data.getTicketNumber());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
