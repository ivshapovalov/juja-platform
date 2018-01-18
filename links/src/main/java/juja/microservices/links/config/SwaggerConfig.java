package juja.microservices.links.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("juja.microservices.links.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Links API Documentation",
                "API documentation for Links service",
                "1.0",
                "Terms of service",
                new Contact("JujaLabs", "https://juja.com.ua/", "info@juja.com.ua"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
    }
}