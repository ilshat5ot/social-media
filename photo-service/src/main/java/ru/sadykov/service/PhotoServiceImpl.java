package ru.sadykov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.dto.PhotoDto;
import ru.sadykov.entity.BinaryContent;
import ru.sadykov.entity.Photo;
import ru.sadykov.entity.PhotoMetaData;
import ru.sadykov.localization.LocalizationExceptionMessage;
import ru.sadykov.repository.BinaryContentRepository;
import ru.sadykov.repository.PhotoMetaDataRepository;
import ru.sadykov.repository.PhotoRepository;
import ru.sadykov.util.Photos;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    @Value("${base-url}")
    private String BASE_URL;

    @Value("#{'${images.sizes}'.split(',')}")
    private List<Integer> imagesWidth;

    private final BinaryContentRepository binaryContentRepository;
    private final PhotoProcessor photoProcessor;
    private final LocalizationExceptionMessage localizationExceptionMessage;
    private final PhotoRepository photoRepository;
    private final PhotoMetaDataRepository photoMetaDataRepository;

    @Override
    public String savePhoto(MultipartFile file) {

        BufferedImage originalPhoto = photoProcessor.readPhoto(file);
        int originalWidth = originalPhoto.getWidth();
        int originalHeight = originalPhoto.getHeight();
        LinkedHashMap<Integer, Integer> imageWidthAndHeight = new LinkedHashMap<>();
        imageWidthAndHeight.put(originalWidth, originalHeight);

        imagesWidth.forEach(width -> imageWidthAndHeight
                .put(width, photoProcessor.calculatePhotoHeight(originalWidth, originalHeight, width)));

        String originalFilename = file.getOriginalFilename();
        String photoExtension = Photos.getPhotoExtension(originalFilename);
        List<BinaryContent> binaryContents = imageWidthAndHeight.entrySet().stream()
                .skip(1)
                .map(entry -> {
                    int width = entry.getKey();
                    int height = entry.getValue();
                    BufferedImage bufferedImage = photoProcessor.resizePhoto(originalPhoto, width, height);
                    byte[] photoAsByteArray = photoProcessor.convertPhotoToByteArray(bufferedImage, photoExtension);
                    return new BinaryContent(null, photoAsByteArray);
                })
                .toList();

        byte[] originalImageAsByteArray = photoProcessor.convertPhotoToByteArray(originalPhoto, photoExtension);

        ArrayList<BinaryContent> photoBytes = new ArrayList<>(binaryContents);
        photoBytes.add(0, new BinaryContent(null, originalImageAsByteArray));

        List<String> binaryContentIds = photoBytes.stream()
                .map(
                        bytes -> {
                            BinaryContent binaryContent = binaryContentRepository.save(bytes);
                            return binaryContent.getId();
                        }
                )
                .toList();

        PhotoMetaData photoMetaData = new PhotoMetaData();
        photoMetaData.setPhotoName(originalFilename);
        photoMetaData.setMediaType(MediaType.IMAGE_PNG);
        photoMetaData.setArchive(false);

        PhotoMetaData savedMetaData = photoMetaDataRepository.save(photoMetaData);


        AtomicInteger i = new AtomicInteger(0);
        List<Photo> photos = imageWidthAndHeight.entrySet().stream()
                .map(entry -> {
                    int width = entry.getKey();
                    int height = entry.getValue();
                    Photo photo = new Photo();
                    photo.setWeight(width);
                    photo.setHeight(height);
                    String binaryContentId = binaryContentIds.get(i.getAndIncrement());
                    photo.setBinaryContentId(binaryContentId);
                    photo.setPhotoMetaData(savedMetaData);
                    return photo;
                })
                .toList();
        List<Photo> savedPhotos = photoRepository.saveAll(photos);

        return null;
    }

    @Override
    public PhotoDto getPhoto(String photoId, Integer width, Integer height) {
//        var savedPhoto = binaryContentRepository
//                .findById(photoId)
//                .orElseThrow(() -> new PhotoNotFoundException(localizationExceptionMessage.getPhotoNotFoundExc()));
//
//        String photoExtension = Photos.getPhotoExtension(savedPhoto.getFileName());
//        MediaType mediaType;
//        if (photoExtension.equals("png")) {
//            mediaType = MediaType.IMAGE_PNG;
//        } else {
//            mediaType = MediaType.IMAGE_JPEG;
//        }
//
//        if (width != null || height != null) {
//            Map<String, List<PhotoSize>> resizePhoto = savedPhoto.getResizePhoto();
//            List<PhotoSize> fixedWidth = resizePhoto.get("fixed_width");
//            Optional<PhotoSize> foundPhoto = fixedWidth.stream()
//                    .filter(photoSize -> width.equals(photoSize.getWidth()) && height.equals(photoSize.getHeight()))
//                    .findFirst();
//            PhotoSize photoSize = foundPhoto.get();
//
//        } else {
//
//        }
//        return PhotoDto.builder()
//                .fileAsByteArrays(savedPhoto.getOriginalPhoto())
//                .mediaType(mediaType)
//                .build();
        return null;
    }
}
