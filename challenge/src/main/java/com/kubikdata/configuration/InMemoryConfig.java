package com.kubikdata.configuration;

import com.kubikdata.repository.SessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class InMemoryConfig {

    @Bean
    @Scope("singleton")
    public SessionRepository tokenToDate() {
        return new SessionRepository();
    }
}
