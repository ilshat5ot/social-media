package ru.sadykov.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Photos {

    public String getImageExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        } else {
            return "";
        }
    }
}
