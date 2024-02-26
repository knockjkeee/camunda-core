package ru.multisys.workflow.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.multisys.workflow.domain.TicketGetDTO;
import ru.multisys.workflow.domain.handleServices.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author knockjkeee
 * @created 26/02/2024 - 13:54
 */


@Log4j2
@Component
//@RequiredArgsConstructor
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OtrsService {

    private final RestTemplate restTemplate;

    @Value("${login.camunda}")
    private String login;

    @Value("${password.camunda}")
    private String password;

    @Value("${path.otrs}")
    private String pathOtrs;

    public OtrsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTask(String id) {
        Ticket ticket = getTicketOperationGet(Long.valueOf(id));
        return ticket != null ? ticket.getState() : null;
    }

    public Ticket getTicketOperationGet(Long id) {

        HttpEntity<Map<String, Object>> requestEntity = getRequestHeaderTickerGet(id);

        ResponseEntity<TicketGetDTO> response = restTemplate.exchange(pathOtrs,
                HttpMethod.POST,
                requestEntity,
                TicketGetDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            TicketGetDTO body = response.getBody();
            List<Ticket> tickets = body != null ? body.getTickets() : null;

            return tickets != null && !tickets.isEmpty() ? tickets.get(0) : null;

        } else {
            return null;
        }
    }

    private HttpEntity<Map<String, Object>> getRequestHeaderTickerGet(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("UserLogin", login);
        map.put("Password", password);
        map.put("TicketID", id);
        return new HttpEntity<>(map, getHttpHeaders());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}
