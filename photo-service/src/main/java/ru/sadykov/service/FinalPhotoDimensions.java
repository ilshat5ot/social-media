package ru.sadykov.service;

import java.util.HashMap;

/**
 * Класс содержит различные размеры изображений.
 * Сделано для что бы подгружать не полноформатные изображения, тем самым ускоряя запрос к БД
 */
public final class FinalPhotoDimensions {

    public static HashMap<Integer, Integer> photoSize = new HashMap<>();

    static {
        photoSize.put(320, null);
        photoSize.put(480, null);
    }
}
