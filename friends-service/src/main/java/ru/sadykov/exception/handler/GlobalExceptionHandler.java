package ru.sadykov.exception.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sadykov.dto.ExceptionMessageDto;
import ru.sadykov.dto.ValidationErrorResponse;
import ru.sadykov.exception.exceptions.AddAsAFriendException;
import ru.sadykov.exception.exceptions.UserNotFoundException;
import ru.sadykov.validators.Violation;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AddAsAFriendException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionMessageDto handleAddAsAFriendException(AddAsAFriendException exception) {
        return new ExceptionMessageDto(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessageDto handleUserNotFoundException(UserNotFoundException exception) {
        return new ExceptionMessageDto(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .toList();
        return new ValidationErrorResponse(violations);
    }
}
