package ua.opnu.labwork4.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eventManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API системи управління подіями")
                        .version("1.0.0")
                        .description("REST API сервіс для автоматизації процесів планування, організації та проведення конференцій та мітапів. " +
                                "Додаток підтримує повний життєвий цикл управління подіями: координацію організаторів, категоризацію заходів, " +
                                "реєстрацію учасників з відстеженням статусів, а також пошук та аналітику.")
                        .contact(new Contact()
                                .name("Кафедра комп'ютерних систем ОНПУ")
                                .email("9101447@stud.op.edu.ua")
                                .url("https://op.edu.ua"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}