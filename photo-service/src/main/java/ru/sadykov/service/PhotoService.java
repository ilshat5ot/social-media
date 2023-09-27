package ru.sadykov.service;

import ru.sadykov.dto.RequestPhotoDto;
import ru.sadykov.entity.Photo;

public interface PhotoService {

    String savePhoto(RequestPhotoDto requestPhotoDto);

    Photo getPhoto(String photoId);
}
