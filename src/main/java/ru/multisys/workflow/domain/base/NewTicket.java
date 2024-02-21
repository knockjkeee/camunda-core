package ru.multisys.workflow.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewTicket(
        @JsonProperty("ticketID") String ticketID,
        @JsonProperty("ticketNumber") String ticketNumber,
        @JsonProperty("customerUserID") String customerUserID,
        @JsonProperty("userID") String userID,
        @JsonProperty("userEmail") String userEmail,
        @JsonProperty("created") String created)
{
}
