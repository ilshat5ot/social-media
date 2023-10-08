package ru.sadykov.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.entity.Photo;

public interface PhotoService {

    String savePhoto(MultipartFile file);

    Photo getPhoto(String photoId);
}
