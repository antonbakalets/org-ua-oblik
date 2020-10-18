package org.ua.oblik.service.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.ua.oblik.domain.model.EntitiesFactory;
import org.ua.oblik.domain.model.EntitiesFactoryImpl;

@Configuration
@ComponentScan("org.ua.oblik.service.test")
public class TestHelperConfig {

    @Bean
    public EntitiesFactory entitiesFactory() {
        return new EntitiesFactoryImpl();
    }
}
