package ru.sadykov.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.sadykov.validators.PhotoExtensionValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhotoExtensionValidator.class)
@Documented
public @interface PhotoExtension {

    String message() default "{extensions.not.support}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
