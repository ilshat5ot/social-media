package ru.sadykov.localization;

import lombok.Getter;

import java.util.Locale;

@Getter
public class LocalizationExceptionMessage {

    private final ExceptionMessageSource ms = new ExceptionMessageSource();
    private final Locale locale;

    private final String fileReadingExc;
    private final String bufferImageReadingExc;
    private final String photoNotFoundExc;


    public LocalizationExceptionMessage(Locale locale) {
        this.locale = locale;

        this.fileReadingExc = ms.getMessage("file.reading.error");
        this.bufferImageReadingExc = ms.getMessage("buffer.image.reading.error");
        this.photoNotFoundExc = ms.getMessage("photo.not.found");
    }
}
