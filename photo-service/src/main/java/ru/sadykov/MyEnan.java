package ru.sadykov;

import org.springframework.http.MediaType;

public enum MyEnan {

    PNG(MediaType.IMAGE_PNG, "png"),
    JPG(MediaType.IMAGE_JPEG, "jpg");

    private final MediaType type;
    private final String extension;

    MyEnan(MediaType type, String extension) {
        this.type = type;
        this.extension = extension;
    }

    public MediaType toMediaType() {
        return null;
    }
}
