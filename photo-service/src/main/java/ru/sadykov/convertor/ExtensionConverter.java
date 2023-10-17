package ru.sadykov.convertor;

import org.springframework.http.MediaType;

public enum ExtensionConverter {

    PNG(MediaType.IMAGE_PNG, "png"),
    JPG(MediaType.IMAGE_JPEG, "jpg");

    private final MediaType type;
    private final String extension;

    ExtensionConverter(MediaType type, String extension) {
        this.type = type;
        this.extension = extension;
    }

    public MediaType toMediaType(String extension) {
        MediaType mediaType = MediaType.IMAGE_PNG;
        if (extension.equals(JPG.extension)) {
            mediaType = JPG.type;
        }
        return mediaType;
    }
}
