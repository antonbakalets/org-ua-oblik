package org.ua.oblik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
        "classpath:/org/ua/oblik/context/dao-context.xml",
        "classpath:/org/ua/oblik/context/jpa-context.xml",
        "classpath:/org/ua/oblik/context/service-context.xml"
})
public class ServiceConfig {
}
