package ru.sadykov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sadykov.dto.RequestPhotoDto;
import ru.sadykov.service.PhotoService;

@RestController
@RequestMapping("/api/v1/photo")

@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    public ResponseEntity<?> savePhoto(@RequestBody RequestPhotoDto requestPhotoDto) {

        String url = photoService.savePhoto(requestPhotoDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(url);
    }

}
