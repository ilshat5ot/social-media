package ru.sadykov.entity;

import lombok.Data;

@Data
public class PhotoSize {
    private int width;
    private int height;
    private byte[] photoAsByteArray;
}
