package ru.sadykov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoSize {
    private int width;
    private int height;
    private byte[] photoAsByteArray;
}
