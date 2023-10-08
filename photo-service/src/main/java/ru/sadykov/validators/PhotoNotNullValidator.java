package ru.sadykov.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.sadykov.validation.PhotoNotNull;

@Component
@Order(0)
public class PhotoNotNullValidator implements ConstraintValidator<PhotoNotNull, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename == null || originalFilename.trim().equals("");
    }
}
