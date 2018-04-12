package org.ua.oblik.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ComponentScan(basePackages = "org.ua.oblik.rest")
@ImportResource({
        "classpath:/org/ua/oblik/context/dao-context.xml",
        "classpath:/org/ua/oblik/context/jpa-context.xml",
        "classpath:/org/ua/oblik/context/service-context.xml"
})
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metadata())
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("\\/v1\\/.*"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Bearer", "header");
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Oblik API")
                .description("Oblik REST API")
                .version("1.0")
                .contact(new Contact("Anton Bakalets", "", "anton.bakalets@yahoo.com"))
                .build();
    }
}
