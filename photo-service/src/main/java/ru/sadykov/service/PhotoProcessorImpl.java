package ru.sadykov.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PhotoProcessorImpl implements PhotoProcessor {

    @Override
    public BufferedImage readPhoto(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int calculatePhotoHeight(int originalWidth, int originalHeight, int newWidth) {
        return (int) Math.round(originalHeight * (double) newWidth / originalWidth);
    }

    @Override
    public BufferedImage resizePhoto(BufferedImage inputImage, int newWidth, int newHeight) {
        Image resultingImage = inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    @Override
    public byte[] convertPhotoToByteArray(BufferedImage bufferedImage, String imageExtension) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, imageExtension, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}