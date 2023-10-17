package ru.sadykov.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface PhotoProcessor {

    BufferedImage readPhoto(MultipartFile file);

    int calculatePhotoHeight(int originalWidth, int originalHeight, int newWidth);

    BufferedImage resizePhoto(BufferedImage inputImage, int newWidth, int newHeight);

    byte[] convertPhotoToByteArray(BufferedImage bufferedImage, String imageExtension);

}
