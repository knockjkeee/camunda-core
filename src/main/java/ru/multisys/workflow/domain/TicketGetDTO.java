package ru.multisys.workflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.multisys.workflow.domain.handleServices.Ticket;

import java.util.List;

/**
 * @author knockjkeee
 * @created 26/02/2024 - 14:01
 */
@Data
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketGetDTO {
    @JsonProperty("Error")
    private Error error;
    @JsonProperty("Ticket")
    private List<Ticket> tickets;
}
