package ru.sadykov.localization;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Component
public class LocalizationExceptionMessage {

    private final ExceptionMessageSource ms = new ExceptionMessageSource();
    private final Locale locale;

    private final String fileReadingExc;
    private final String bufferImageReadingExc;
    private final String photoNotFoundExc;


    public LocalizationExceptionMessage() {
        this.locale = Locale.getDefault();

        this.fileReadingExc = ms.getMessage("file.reading.error");
        this.bufferImageReadingExc = ms.getMessage("buffer.image.reading.error");
        this.photoNotFoundExc = ms.getMessage("photo.not.found");
    }
}
