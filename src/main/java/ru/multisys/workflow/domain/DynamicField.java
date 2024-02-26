package ru.multisys.workflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DynamicField {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Value")
    private String value;
}
