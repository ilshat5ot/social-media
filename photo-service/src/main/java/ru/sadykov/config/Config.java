package ru.sadykov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sadykov.localization.LocalizationExceptionMessage;

import java.util.Locale;

@Configuration
public class Config {

    @Bean
    public LocalizationExceptionMessage localizationExceptionMessage() {
        return new LocalizationExceptionMessage(Locale.getDefault());
    }
}
