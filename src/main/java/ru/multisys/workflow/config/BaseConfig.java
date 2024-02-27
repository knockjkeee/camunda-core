package ru.multisys.workflow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author knockjkeee
 * @created 21/02/2024 - 12:19
 */
@Configuration
//@ComponentScan("ru.multisys.workflow.service")
public class BaseConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate rst = new RestTemplate();
        rst.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return rst;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                         .addModule(new JavaTimeModule())
                         .build();
    }
}
