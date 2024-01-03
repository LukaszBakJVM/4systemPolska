package org.freeddyns.systempolska.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    NameEncoder nameEncoder() {
        return new NameEncoder();
    }

    @Bean
    StringBuilder str() {
        return new StringBuilder();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
