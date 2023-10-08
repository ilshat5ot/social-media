package ru.sadykov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.service.PhotoService;
import ru.sadykov.validation.PhotoExtension;
import ru.sadykov.validation.PhotoNotNull;

@RestController
@RequestMapping("/api/v1/photo")

@RequiredArgsConstructor
@Validated
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    public ResponseEntity<?> savePhoto(@PhotoNotNull @PhotoExtension @RequestBody MultipartFile file) {

        var url = photoService.savePhoto(file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(url);
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<?> getPhoto(@PathVariable String photoId,
                                      @RequestParam(required = false) Integer width,
                                      @RequestParam(required = false) Integer height) {
        var photo = photoService.getPhoto(photoId, width, height);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(photo);
    }

}
