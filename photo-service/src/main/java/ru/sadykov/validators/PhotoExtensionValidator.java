package ru.sadykov.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.util.Photos;
import ru.sadykov.validation.PhotoExtension;

@Component
@Order(1)
public class PhotoExtensionValidator implements ConstraintValidator<PhotoExtension, MultipartFile> {

    private String message;

    @Override
    public void initialize(PhotoExtension constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String originalFilename = file.getOriginalFilename();
        String photoExtension = Photos.getPhotoExtension(originalFilename).toLowerCase();
        if (!photoExtension.equals("png") && !photoExtension.equals("jpg")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
