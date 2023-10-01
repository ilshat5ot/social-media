package ru.sadykov.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PhotoProcessorImpl implements PhotoProcessor {

    @Override
    public BufferedImage readPhoto(String pathToPhotoAsString) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(pathToPhotoAsString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bufferedImage;
    }

    @Override
    public void calculatePhotoHeight(int originalWidth, int originalHeight) {
        Set<Integer> setWidth = FinalPhotoDimensions.photoSize.keySet();
        for (Integer newWidth : setWidth) {
            int newHeight = (int) Math.round(originalHeight * (double) newWidth / originalWidth);
            FinalPhotoDimensions.photoSize.put(newWidth, newHeight);
        }
    }

    @Override
    public List<BufferedImage> resizePhoto(BufferedImage inputImage) {
        Set<Integer> setWidth = FinalPhotoDimensions.photoSize.keySet();
        List<BufferedImage> bufferedImages = new ArrayList<>();
        for (Integer newWidth : setWidth) {
            Image resultingImage = inputImage.getScaledInstance(newWidth, FinalPhotoDimensions.photoSize.get(newWidth), Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(newWidth, FinalPhotoDimensions.photoSize.get(newWidth), inputImage.getType());
            outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
            bufferedImages.add(outputImage);
        }
        return bufferedImages;
    }

    @Override
    public byte[] convertPhotoToByteArray(BufferedImage bufferedImage, String photoExtension) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, photoExtension, baos);
            baos.flush();
            baos.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }

}
