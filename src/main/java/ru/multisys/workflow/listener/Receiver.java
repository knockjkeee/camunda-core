package ru.multisys.workflow.listener;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.multisys.workflow.database.dao.TasksDao;
import ru.multisys.workflow.database.entity.TasksEntity;
import ru.multisys.workflow.domain.NewTicket;
import ru.multisys.workflow.domain.StateTicket;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * @author knockjkeee
 * @created 21/02/2024 - 12:20
 */
@Log4j2
@Component
public class Receiver {

    @Value("${camunda.endpoint}")
    private String url;

    private final RestTemplate restTemplate;

    private final ProcessEngine processEngine;

//    private final TasksDao tasksDao;

    public Receiver(RestTemplate restTemplate, ProcessEngine processEngine) {
        this.restTemplate = restTemplate;
        this.processEngine = processEngine;
//        this.tasksDao = tasksDao;
    }

//    CountDownLatch latch;

    @KafkaListener(topics = "advice-topic", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenAsObject(ConsumerRecord<String, NewTicket> cr,
                               @Payload NewTicket payload) {
        log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());

        // init processEngine
        Map<String, Object> req = new HashMap<>();
        req.put("ticket", payload);
        Instant now = Instant.now();
        req.put("initTime", now.toString());

        processEngine
                .getRuntimeService()
                .startProcessInstanceByMessage("StartProcess", "pub-1", req);

        log.info("Response: {}",  payload);

//        String processInstanceId = startProcess.getProcessInstanceId();


        // init rest
//        HttpEntity<NewTicket> request = new HttpEntity<>(payload);
//        var response = restTemplate.exchange(url, HttpMethod.POST, request, NewTicket.class);
//        log.info("Response: {}",  response);

//        latch.countDown();
    }

//    @KafkaListener(topics = "advice-topic", clientIdPrefix = "string",
//            containerFactory = "kafkaListenerStringContainerFactory")
//    public void listenasString(ConsumerRecord<String, String> cr,
//                               @Payload String payload) {
//        log.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
//                typeIdHeader(cr.headers()), payload, cr.toString());
//        latch.countDown();
//    }
//
//    @KafkaListener(topics = "advice-topic", clientIdPrefix = "bytearray",
//            containerFactory = "kafkaListenerByteArrayContainerFactory")
//    public void listenAsByteArray(ConsumerRecord<String, byte[]> cr,
//                                  @Payload byte[] payload) {
//        log.info("Logger 3 [ByteArray] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
//                typeIdHeader(cr.headers()), payload, cr.toString());
//        latch.countDown();
//    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport
                .stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
