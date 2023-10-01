package ru.sadykov.service;

import java.awt.image.BufferedImage;
import java.util.List;

public interface PhotoProcessor {

    BufferedImage readPhoto(String pathToPhotoAsString);

    void calculatePhotoHeight(int originalWidth, int originalHeight);

    List<BufferedImage> resizePhoto(BufferedImage inputImage);

    byte[] convertPhotoToByteArray(BufferedImage bufferedImage, String photoExtension);

}
