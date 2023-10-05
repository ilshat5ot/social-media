package ru.sadykov.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.sadykov.validators.UserExistValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserExistValidator.class)
@Documented
public @interface UserExist {

    String message() default "{user.not.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
