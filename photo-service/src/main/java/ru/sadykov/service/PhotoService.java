package ru.sadykov.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.entity.Photo;

import java.util.List;

public interface PhotoService {

    List<Photo> savePhoto(MultipartFile file);
}
