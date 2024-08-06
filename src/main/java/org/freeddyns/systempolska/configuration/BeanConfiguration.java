package org.freeddyns.systempolska.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {

    @Bean
    NameEncoder nameEncoder() {
        return new NameEncoder();
    }

    @Bean
    StringBuilder str() {
        return new StringBuilder();
    }

}


