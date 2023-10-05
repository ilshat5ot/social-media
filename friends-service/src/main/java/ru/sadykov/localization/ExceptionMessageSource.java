package ru.sadykov.localization;

import lombok.Getter;
import org.springframework.context.support.ResourceBundleMessageSource;

@Getter
public class ExceptionMessageSource extends ResourceBundleMessageSource {

    private static final String BASE_NAME = "ExceptionMessages";

    public ExceptionMessageSource() {
        this(BASE_NAME);
    }

    private ExceptionMessageSource(String... baseNames) {
        super();
        setUseCodeAsDefaultMessage(true);
        setDefaultEncoding("UTF-8");
        addBasenames(baseNames);
    }
}
