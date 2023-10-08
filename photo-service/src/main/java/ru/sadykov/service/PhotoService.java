package ru.sadykov.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.dto.PhotoDto;

public interface PhotoService {

    String savePhoto(MultipartFile file);

    PhotoDto getPhoto(String photoId, Integer width, Integer height);
}
