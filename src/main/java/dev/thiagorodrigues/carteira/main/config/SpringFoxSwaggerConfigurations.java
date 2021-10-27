package dev.thiagorodrigues.carteira.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
public class SpringFoxSwaggerConfigurations {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("dev.thiagorodrigues.carteira.application.controllers"))
                .paths(PathSelectors.any()).build().globalRequestParameters(Arrays.asList(new RequestParameterBuilder()
                        .name("Authorization").description("Bearer Token").required(false).in("header").build()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API Carteira de Investimentos").description("Documentação")
                .license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .termsOfServiceUrl("https://google.com.br").version("1.0.0")
                .contact(new Contact("John Doe", "google.com.br", "john@mail.com")).build();
    }

}
