package ru.sadykov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sadykov.dto.RequestPhotoDto;
import ru.sadykov.entity.Photo;
import ru.sadykov.entity.PhotoSize;
import ru.sadykov.repository.PhotoRepository;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    @Value("${base-url}")
    private String BASE_URL;

    private final PhotoRepository photoRepository;
    private final PhotoProcessor photoProcessor;


    @Override
    public String savePhoto(RequestPhotoDto requestPhotoDto) {

        BufferedImage originalPhoto = photoProcessor.readPhoto(requestPhotoDto.pathPhoto());

        int originalWidth = originalPhoto.getWidth();
        int originalHeight = originalPhoto.getHeight();

        photoProcessor.calculatePhotoHeight(originalWidth, originalHeight);

        List<BufferedImage> bufferedImages = photoProcessor.resizePhoto(originalPhoto);

        var photoExtension = Photos.getPhotoExtension(requestPhotoDto.pathPhoto());

        byte[] originalPhotoAsByteArray = photoProcessor.convertPhotoToByteArray(originalPhoto, photoExtension);

        List<byte[]> resizePhotoAsByteArray = new ArrayList<>();
        for (BufferedImage bufferedImage : bufferedImages) {
            resizePhotoAsByteArray.add(photoProcessor.convertPhotoToByteArray(bufferedImage, photoExtension));
        }
        Set<Integer> listPhotoSize = FinalPhotoDimensions.photoSize.keySet();

        HashMap<String, List<PhotoSize>> map = new HashMap<>();
        List<PhotoSize> list = new ArrayList<>();
        int i = 0;
        for (Integer width : listPhotoSize) {
            PhotoSize photoSize = new PhotoSize();
            photoSize.setWidth(width);
            photoSize.setHeight(FinalPhotoDimensions.photoSize.get(width));
            photoSize.setPhotoAsByteArray(resizePhotoAsByteArray.get(i++));
            list.add(photoSize);
        }
        map.put("fixed_width", list);


        var photoName = Photos.getPhotoName(requestPhotoDto.pathPhoto());
        var transientPhoto = Photo
                .builder()
                .fileName(photoName)
                .originalPhoto(originalPhotoAsByteArray)
                .resizePhoto(map)
                .dateTime(LocalDateTime.now())
                .build();

        var savedPhoto = photoRepository.save(transientPhoto);

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
