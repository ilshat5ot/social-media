package ru.sadykov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        var url = photoService.savePhoto(requestPhotoDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(url);
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<?> getPhoto(@PathVariable String photoId) {
        var photo = photoService.getPhoto(photoId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(null);
    }

}
