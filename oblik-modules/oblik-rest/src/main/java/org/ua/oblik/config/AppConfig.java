package org.ua.oblik.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.ua.oblik.rest.v1")
public class AppConfig {
}
