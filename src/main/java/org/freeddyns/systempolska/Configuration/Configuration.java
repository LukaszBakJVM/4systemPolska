package org.freeddyns.systempolska.Configuration;


import org.springframework.context.annotation.Bean;



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
}


