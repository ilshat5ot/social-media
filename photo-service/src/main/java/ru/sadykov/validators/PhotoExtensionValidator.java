package ru.sadykov.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.properties.PhotoProperties;
import ru.sadykov.util.Photos;
import ru.sadykov.validation.PhotoExtension;

@Order(1)
@Component
@RequiredArgsConstructor
public class PhotoExtensionValidator implements ConstraintValidator<PhotoExtension, MultipartFile> {

    private String message;
    private final PhotoProperties photoProperties;

    @Override
    public void initialize(PhotoExtension constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String originalFilename = file.getOriginalFilename();
        String photoExtension = Photos.getPhotoExtension(originalFilename).toLowerCase();
        if (!photoProperties.extension().contains(photoExtension)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
