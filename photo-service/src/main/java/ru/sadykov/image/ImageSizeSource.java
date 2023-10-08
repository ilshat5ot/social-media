package ru.sadykov.image;

import org.springframework.context.support.ResourceBundleMessageSource;

public class ImageSizeSource extends ResourceBundleMessageSource {

    private static final String BASE_NAME = "ExceptionMessages";

    public ImageSizeSource() {
        this(BASE_NAME);
    }

    private ImageSizeSource(String... baseNames) {
        super();
        setUseCodeAsDefaultMessage(true);
        setDefaultEncoding("UTF-8");
        addBasenames(baseNames);
    }
}
