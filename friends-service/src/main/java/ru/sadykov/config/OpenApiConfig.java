package ru.sadykov.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Follower Service Api",
                description = "Сервис подписчиков", version = "1.0.0",
                contact = @Contact(
                        name = "Sadykov Ilshat",
                        email = "ilshat5ot@gmail.com"
                )
        )
)
public class OpenApiConfig {
}