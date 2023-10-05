package ru.sadykov.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.client.UserFeignClient;
import ru.sadykov.validation.UserExist;

@Component
@RequiredArgsConstructor
public class UserExistValidator implements ConstraintValidator<UserExist, Long> {

    private final UserFeignClient userFeignClient;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return userFeignClient.existUser(value);
    }
}
