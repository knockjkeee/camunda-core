package ru.multisys.workflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewTicket{
    @JsonProperty("ticketID")
    String ticketID;
    @JsonProperty("ticketNumber")
    String ticketNumber;
    @JsonProperty("customerUserID")
    String customerUserID;
    @JsonProperty("userID")
    String userID;
    @JsonProperty("userEmail")
    String userEmail;
    @JsonProperty("created")
    String created;
    @JsonProperty("state")
    String state;
}
