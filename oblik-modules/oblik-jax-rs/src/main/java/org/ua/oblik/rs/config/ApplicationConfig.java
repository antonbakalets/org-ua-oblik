package org.ua.oblik.rs.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.ua.oblik.rs.resource.BudgetResource;
import org.ua.oblik.rs.resource.CurrencyResource;

@Configuration
@ComponentScan(basePackages = "org.ua.oblik.rs.resource")
@ImportResource({
        "classpath:/org/ua/oblik/context/dao-context.xml",
        "classpath:/org/ua/oblik/context/jpa-context.xml",
        "classpath:/org/ua/oblik/context/service-context.xml"
})
@SpringBootApplication
@ApplicationPath("/rs")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(BudgetResource.class);
        register(CurrencyResource.class);
        register(NotFoundMapper.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
