package ru.sadykov.localization;

import lombok.Getter;
import org.springframework.context.support.ResourceBundleMessageSource;

@Getter
public class MessageSource extends ResourceBundleMessageSource {

    private static final String BASE_NAME = "ExceptionMessages";

    public MessageSource() {
        this(BASE_NAME);
    }

    private MessageSource(String... baseNames) {
        super();
        setUseCodeAsDefaultMessage(true);
        setDefaultEncoding("UTF-8");
        addBasenames(baseNames);
    }
}
