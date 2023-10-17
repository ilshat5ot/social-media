package ru.sadykov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.convertor.ExtensionConverter;
import ru.sadykov.entity.BinaryContent;
import ru.sadykov.entity.Photo;
import ru.sadykov.entity.PhotoMetaData;
import ru.sadykov.properties.PhotoProperties;
import ru.sadykov.repository.BinaryContentRepository;
import ru.sadykov.repository.PhotoMetaDataRepository;
import ru.sadykov.repository.PhotoRepository;
import ru.sadykov.util.Photos;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    @Value("${base-url}")
    private String BASE_URL;

    private final PhotoProperties photoProperties;
    private final BinaryContentRepository binaryContentRepository;
    private final PhotoProcessor photoProcessor;
    private final PhotoRepository photoRepository;
    private final PhotoMetaDataRepository photoMetaDataRepository;

    private static final ExtensionConverter extensionConverter = ExtensionConverter.PNG;

    @Override
    public List<Photo> savePhoto(MultipartFile file) {

        BufferedImage originalPhotoBuffer = photoProcessor.readPhoto(file);

        int originalWidth = originalPhotoBuffer.getWidth();
        int originalHeight = originalPhotoBuffer.getHeight();

        List<Integer> widthsPhoto = photoProperties.widths();
        Map<Integer, Integer> photoSizes = widthsPhoto.stream()
                .collect(Collectors.toMap(
                        width -> width,
                        width -> photoProcessor.calculatePhotoHeight(originalWidth, originalHeight, width))
                );

        String originalFilename = file.getOriginalFilename();
        String photoExtension = Photos.getPhotoExtension(originalFilename);
        List<BinaryContent> binaryContentResizedPhotos = photoSizes.entrySet().stream()
                .map(entry -> {
                    int width = entry.getKey();
                    int height = entry.getValue();
                    BufferedImage bufferedImage = photoProcessor.resizePhoto(originalPhotoBuffer, width, height);
                    byte[] photoAsByteArray = photoProcessor.convertPhotoToByteArray(bufferedImage, photoExtension);
                    BinaryContent binaryContent = new BinaryContent();
                    binaryContent.setPhoto(photoAsByteArray);
                    return binaryContent;
                })
                .toList();

        List<String> binaryContentIds = binaryContentResizedPhotos.stream()
                .map(bytes -> {
                            BinaryContent binaryContent = binaryContentRepository.save(bytes);
                            return binaryContent.getId();
                        })
                .toList();
        byte[] originalPhotoAsByteArray = photoProcessor.convertPhotoToByteArray(originalPhotoBuffer, photoExtension);
        BinaryContent binaryContentOriginalPhoto = new BinaryContent();
        BinaryContent savedBinaryContentOriginalPhoto = binaryContentRepository.save(binaryContentOriginalPhoto);

        PhotoMetaData photoMetaData = new PhotoMetaData();
        photoMetaData.setPhotoName(originalFilename);
        photoMetaData.setMediaType(photoExtension);

        PhotoMetaData savedMetaData = photoMetaDataRepository.save(photoMetaData);


        List<Photo> photos1 = IntStream.range(0, photoSizes.size())
                .mapToObj(i -> {
                    Photo photo = new Photo();
                    Integer widthPhoto = widthsPhoto.get(i);
                    photo.setWeight(widthPhoto);
                    photo.setHeight(photoSizes.get(widthPhoto));
                    photo.setPhotoMetaData(savedMetaData);
                    photo.setBinaryContentId(binaryContentIds.get(i));
                    return photo;
                })
                .toList();

        binaryContentOriginalPhoto.setPhoto(originalPhotoAsByteArray);

        Photo originalPhoto = new Photo();
        originalPhoto.setWeight(originalWidth);
        originalPhoto.setHeight(originalHeight);
        originalPhoto.setPhotoMetaData(savedMetaData);
        originalPhoto.setBinaryContentId(savedBinaryContentOriginalPhoto.getId());

        List<Photo> photos = photoRepository.saveAll(photos1);
        Photo save = photoRepository.save(originalPhoto);
        ArrayList<Photo> photos2 = new ArrayList<>(photos);
        photos2.add(save);
        return photos2;
    }
}
