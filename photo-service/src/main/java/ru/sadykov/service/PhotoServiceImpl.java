package ru.sadykov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.entity.Photo;
import ru.sadykov.entity.PhotoSize;
import ru.sadykov.repository.PhotoRepository;
import ru.sadykov.util.Photos;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    @Value("${base-url}")
    private String BASE_URL;

    @Value("#{'${images.sizes}'.split(',')}")
    private List<Integer> imagesWidth;

    private final PhotoRepository photoRepository;
    private final PhotoProcessor photoProcessor;

    @Override
    public String savePhoto(MultipartFile file) {

        BufferedImage originalPhoto = photoProcessor.readPhoto(file);
        int originalWidth = originalPhoto.getWidth();
        int originalHeight = originalPhoto.getHeight();
        Map<Integer, Integer> imageWidthAndHeight = imagesWidth.stream()
                .collect(Collectors.toMap(
                        key -> key,
                        value -> photoProcessor.calculatePhotoHeight(originalWidth, originalHeight, value))
                );

        String photoExtension = Photos.getImageExtension(Objects.requireNonNull(file.getOriginalFilename()));
        List<PhotoSize> photoSizes = imageWidthAndHeight.entrySet().stream()
                .map(entry -> {
                    int width = entry.getKey();
                    int height = entry.getValue();
                    BufferedImage bufferedImage = photoProcessor.resizePhoto(originalPhoto, width, height);
                    byte[] photoAsByteArray = photoProcessor.convertPhotoToByteArray(bufferedImage, photoExtension);
                    return new PhotoSize(width, height, photoAsByteArray);
                })
                .toList();

        byte[] originalImageAsByteArray = photoProcessor.convertPhotoToByteArray(originalPhoto, photoExtension);

        Photo transientPhoto = Photo.builder()
                .fileName(file.getOriginalFilename())
                .originalPhoto(originalImageAsByteArray)
                .resizePhoto(Map.of("fixed_width", photoSizes))
                .dateTime(LocalDateTime.now())
                .build();

        Photo savedPhoto = photoRepository.save(transientPhoto);
        return BASE_URL + savedPhoto.getId();
    }

    @Override
    public Photo getPhoto(String photoId) {
        var savedPhoto = photoRepository
                .findById(photoId)
                .orElseThrow(() -> new RuntimeException("Фото не найдено!"));
        return savedPhoto;
    }
}
