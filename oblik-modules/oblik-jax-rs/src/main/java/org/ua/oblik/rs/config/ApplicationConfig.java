package org.ua.oblik.rs.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.ua.oblik.rs.resource.BudgetResource;
import org.ua.oblik.rs.resource.CurrencyResource;
import org.ua.oblik.soap.client.RedirectService;
import org.ua.oblik.soap.client.RedirectServiceImplService;

@Configuration
@ComponentScan(basePackages = "org.ua.oblik.rs.resource")
@SpringBootApplication
@ApplicationPath("/rs")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(BudgetResource.class);
        register(CurrencyResource.class);
        register(RedirectExceptionMapper.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Bean
    public RedirectService redirectService() {
        RedirectServiceImplService redirectServiceImplService = new RedirectServiceImplService();
        return redirectServiceImplService.getRedirectServiceImplPort();
    }
}
