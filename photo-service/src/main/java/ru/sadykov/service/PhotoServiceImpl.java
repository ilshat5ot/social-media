package ru.sadykov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sadykov.dto.RequestPhotoDto;
import ru.sadykov.entity.Photo;
import ru.sadykov.repository.PhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private static final String BASE_URL = "http://127.0.0.1:8083/api/v1/photo/";

    @Override
    public String savePhoto(RequestPhotoDto requestPhotoDto) {

        var urlAsString = requestPhotoDto.url();
        var path = Paths.get(urlAsString);

        try {
            byte[] arrayByteOfPhoto = Files.readAllBytes(path);
            var photoName = path.getFileName().toString();

            var transientPhoto = Photo
                    .builder()
                    .fileName(photoName)
                    .fileAsArrayOfBytes(arrayByteOfPhoto)
                    .dateTime(LocalDateTime.now())
                    .build();

            var savedPhoto = photoRepository.save(transientPhoto);

            return BASE_URL + savedPhoto.getId();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Photo getPhoto(String photoId) {
        var savedPhoto = photoRepository
                .findById(photoId)
                .orElseThrow(() -> new RuntimeException("Фото не найдено!"));
        return savedPhoto;
    }
}
