package ru.sadykov.service;

import ru.sadykov.dto.RequestPhotoDto;

public interface PhotoService {

    String savePhoto(RequestPhotoDto requestPhotoDto);
}
